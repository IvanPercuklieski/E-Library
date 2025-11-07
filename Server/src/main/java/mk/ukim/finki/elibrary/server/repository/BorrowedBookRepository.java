package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByUser(UserWrapper user);
}
