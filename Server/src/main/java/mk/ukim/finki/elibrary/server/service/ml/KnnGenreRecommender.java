package mk.ukim.finki.elibrary.server.service.ml;

import mk.ukim.finki.elibrary.server.dto.GenreScoreDTO;
import mk.ukim.finki.elibrary.server.dto.UserGenreRecommendationDTO;
import mk.ukim.finki.elibrary.server.repository.BorrowedBookLogRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KnnGenreRecommender {

    private final BorrowedBookLogRepository borrowLogRepository;

    public KnnGenreRecommender(BorrowedBookLogRepository borrowLogRepository) {
        this.borrowLogRepository = borrowLogRepository;
    }


    public UserGenreRecommendationDTO recommendGenresForUser(Long userId,
                                                             int k,
                                                             int maxRecommendations) {

        var rows = borrowLogRepository.findAllUserGenreCounts();
        Map<Long, UserGenreVector> userVectors = new HashMap<>();
        Map<Long, String> genreNames = new HashMap<>();

        for (BorrowedBookLogRepository.UserGenreCountProjection row : rows) {
            Long uId = row.getUserId();
            Long gId = row.getGenreId();
            String gName = row.getGenreName();
            long count = row.getBorrowCount();

            genreNames.putIfAbsent(gId, gName);

            UserGenreVector vec = userVectors.computeIfAbsent(uId, id -> {
                UserGenreVector v = new UserGenreVector();
                v.userId = id;
                return v;
            });

            vec.genreCounts.merge(gId, (double) count, Double::sum);
        }

        UserGenreVector target = userVectors.get(userId);
        if (target == null || target.genreCounts.isEmpty()) {
            return new UserGenreRecommendationDTO(userId, List.of());
        }

        userVectors.values().forEach(UserGenreVector::recomputeNorm);


        List<Map.Entry<Long, Double>> sims = new ArrayList<>();
        for (UserGenreVector other : userVectors.values()) {
            if (Objects.equals(other.userId, userId)) continue;

            double sim = cosineSimilarity(target, other);
            if (sim > 0) {
                sims.add(Map.entry(other.userId, sim));
            }
        }


        sims.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        List<Map.Entry<Long, Double>> neighbours = sims.stream()
                .limit(k)
                .toList();


        Map<Long, Double> aggregatedScores = new HashMap<>();

        for (Map.Entry<Long, Double> neigh : neighbours) {
            Long neighUserId = neigh.getKey();
            double sim = neigh.getValue();
            UserGenreVector neighVec = userVectors.get(neighUserId);
            if (neighVec == null) continue;

            for (Map.Entry<Long, Double> gc : neighVec.genreCounts.entrySet()) {
                Long genreId = gc.getKey();
                double cnt = gc.getValue();
                aggregatedScores.merge(genreId, sim * cnt, Double::sum);
            }
        }

        if (aggregatedScores.isEmpty()) {
            List<GenreScoreDTO> own = target.genreCounts.entrySet().stream()
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .limit(maxRecommendations)
                    .map(e -> new GenreScoreDTO(
                            e.getKey(),
                            genreNames.getOrDefault(e.getKey(), "Unknown"),
                            1.0,
                            e.getValue().longValue()
                    ))
                    .collect(Collectors.toList());

            return new UserGenreRecommendationDTO(userId, own);
        }

        double maxScore = aggregatedScores.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(1.0);

        List<GenreScoreDTO> recs = aggregatedScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(maxRecommendations)
                .map(e -> {
                    Long genreId = e.getKey();
                    double raw = e.getValue();
                    double normalized = raw / maxScore;

                    long ownCount = target.genreCounts
                            .getOrDefault(genreId, 0.0)
                            .longValue();

                    return new GenreScoreDTO(
                            genreId,
                            genreNames.getOrDefault(genreId, "Unknown"),
                            normalized,
                            ownCount
                    );
                })
                .collect(Collectors.toList());

        return new UserGenreRecommendationDTO(userId, recs);
    }

    private double cosineSimilarity(UserGenreVector a, UserGenreVector b) {
        if (a.norm == 0 || b.norm == 0) return 0.0;

        double dot = 0.0;
        Map<Long, Double> small = a.genreCounts.size() < b.genreCounts.size()
                ? a.genreCounts : b.genreCounts;
        Map<Long, Double> large = (small == a.genreCounts ? b.genreCounts : a.genreCounts);

        for (Map.Entry<Long, Double> e : small.entrySet()) {
            Double v2 = large.get(e.getKey());
            if (v2 != null) {
                dot += e.getValue() * v2;
            }
        }

        return dot / (a.norm * b.norm);
    }
}