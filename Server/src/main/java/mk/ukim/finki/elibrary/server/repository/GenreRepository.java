package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
