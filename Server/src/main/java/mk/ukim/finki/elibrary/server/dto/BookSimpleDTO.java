package mk.ukim.finki.elibrary.server.dto;

import java.util.List;

public class BookSimpleDTO {

    private Long id;
    private String title;
    private String author;
    private List<String> genres;

    public BookSimpleDTO(Long id, String title, String author, List<String> genres) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genres = genres;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public List<String> getGenres() { return genres; }

    public static BookSimpleDTO fromBaseBook(mk.ukim.finki.elibrary.server.model.domain.BaseBook b) {
        return new BookSimpleDTO(
                b.getId(),
                b.getTitle(),
                b.getAuthor().getName(),
                b.getGenres()
                        .stream()
                        .map(g -> g.getName())
                        .toList()
        );
    }
}
