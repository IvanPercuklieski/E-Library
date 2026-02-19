package mk.ukim.finki.elibrary.server.repository;

import jakarta.transaction.Transactional;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findByBaseBookId(Long baseBookId);

    @Query("SELECT bc FROM BookCopy bc WHERE bc.baseBook.id = :bookId " +
            "AND bc.id NOT IN (SELECT bb.bookCopy.id FROM BorrowedBook bb)")
    List<BookCopy> findAvailableBookCopies(@Param("bookId") Long bookId);

    long countByBaseBookId(Long baseBookId);

    @Query("""
    select c from BookCopy c
    where c.baseBook.id = :baseBookId
      and c.id not in (
          select bb.bookCopy.id from BorrowedBook bb
          where bb.bookCopy.baseBook.id = :baseBookId
      )
    order by c.id asc
""")
    List<BookCopy> findAvailableCopiesForBaseBook(
            @Param("baseBookId") Long baseBookId,
            Pageable pageable
    );
}
