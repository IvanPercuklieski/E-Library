package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author")
    private List<BaseBook> books;

    public Author(String name){
        this.name = capitalizeAuthorNameCase(name);
    }

    public static String capitalizeAuthorNameCase(String authorName) {
        return WordUtils.capitalizeFully(authorName, ' ', '.', '-');
    }
}
