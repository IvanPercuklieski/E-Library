package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseBookRepository extends JpaRepository<BaseBook, Long> {
    @Query("""
        select distinct b
        from BaseBook b
        join b.genres g
        join b.copies c
        left join BookBorrowLog l
               on l.bookCopy = c and l.returnedAt is null
        where g.id = :genreId
          and l.id is null
    """)
    List<BaseBook> findAvailableByGenre(Long genreId);

    @Query("""
        select distinct b
        from BaseBook b
        join b.genres g
        join b.copies c
        left join BookBorrowLog l
               on l.bookCopy = c and l.returnedAt is null
        where g.id = :genreId
          and b.id not in :excludedBookIds
          and l.id is null
    """)
    List<BaseBook> findAvailableByGenreExcludingBorrowed(Long genreId, List<Long> excludedBookIds);
}
