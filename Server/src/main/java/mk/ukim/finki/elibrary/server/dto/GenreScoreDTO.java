package mk.ukim.finki.elibrary.server.dto;

public class GenreScoreDTO {
    private Long genreId;
    private String genreName;
    private double score;
    private long borrowCount;

    public GenreScoreDTO(Long genreId, String genreName, double score, long borrowCount) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.score = score;
        this.borrowCount = borrowCount;
    }

    public Long getGenreId() { return genreId; }
    public String getGenreName() { return genreName; }
    public double getScore() { return score; }
    public long getBorrowCount() { return borrowCount; }
}
