package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface BorrowedBookLogRepository extends JpaRepository<BookBorrowLog, Long> {
    List<BookBorrowLog> findByUser(UserWrapper user);

    interface UserGenreCountProjection {
        Long getUserId();
        Long getGenreId();
        String getGenreName();
        long getBorrowCount();
    }

    @Query("""
        select bbl.user.id as userId,
               g.id as genreId,
               g.name as genreName,
               count(bbl) as borrowCount
        from BookBorrowLog bbl
        join bbl.bookCopy bc
        join bc.baseBook bb
        join bb.genres g
        group by bbl.user.id, g.id, g.name
        """)
    List<UserGenreCountProjection> findAllUserGenreCounts();

    @Query("""
        select distinct c.baseBook.id
        from BookBorrowLog l
        join l.bookCopy c
        where l.user.id = :userId
    """)
    List<Long> findDistinctBaseBookIdsBorrowedByUser(Long userId);

    @Query("""
        select l
        from BookBorrowLog l
        where l.user.id = :userId
        order by l.borrowedAt asc
    """)
    List<BookBorrowLog> findByUserIdOrderByBorrowedAtAsc(Long userId);

}
