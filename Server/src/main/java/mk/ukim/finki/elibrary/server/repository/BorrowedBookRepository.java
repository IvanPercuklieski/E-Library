package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByUser(UserWrapper user);


    boolean existsByBookCopy(BookCopy copy);

    @Query("""
        select count(bb)
        from BorrowedBook bb
        where bb.bookCopy.baseBook.id = :baseBookId
    """)
    long countActiveBorrowingsByBaseBookId(@Param("baseBookId") Long baseBookId);


}
