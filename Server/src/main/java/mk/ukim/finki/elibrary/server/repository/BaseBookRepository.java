package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.Author;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("""
        select distinct b
        from BaseBook b
        left join b.genres g
        where (:title is null or lower(b.title) like lower(concat('%', :title, '%')))
          and (:authorId is null or b.author.id = :authorId)
          and (:genreId is null or g.id = :genreId)
    """)
    List<BaseBook> search(
            @Param("title") String title,
            @Param("authorId") Long authorId,
            @Param("genreId") Long genreId
    );

    @Query("SELECT b FROM BaseBook b JOIN b.genres g WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:author IS NULL OR b.author = :author) " +
            "AND (:genres IS NULL OR g IN :genres)")
    List<BaseBook> searchBooks(@Param("title") String title,
                               @Param("author") Author author,
                               @Param("genres") List<Genre> genres);



    List<BaseBook> findByAuthor(Author author);


    List<BaseBook> findByGenresContains(Genre genre);


    @Query("SELECT b FROM BaseBook b JOIN BorrowedBook bb ON bb.bookCopy.baseBook = b " +
                "WHERE bb.user.id = :userId GROUP BY b ORDER BY COUNT(bb.id) DESC")
    List<BaseBook> findRecommendedBooks(@Param("userId") Long userId);



}
