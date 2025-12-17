package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findByBaseBookId(Long baseBookId);

    @Query("SELECT bc FROM BookCopy bc WHERE bc.baseBook.id = :bookId " +
            "AND bc.id NOT IN (SELECT bb.bookCopy.id FROM BorrowedBook bb)")
    List<BookCopy> findAvailableBookCopies(@Param("bookId") Long bookId);
}
