package mk.ukim.finki.elibrary.server.service.ml;
import java.util.HashMap;
import java.util.Map;

class UserGenreVector {
    Long userId;
    Map<Long, Double> genreCounts = new HashMap<>();
    double norm;

    void recomputeNorm() {
        double sumSq = 0.0;
        for (double v : genreCounts.values()) {
            sumSq += v * v;
        }
        this.norm = Math.sqrt(sumSq);
    }
}
