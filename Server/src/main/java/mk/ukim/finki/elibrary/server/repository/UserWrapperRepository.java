package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.enumerations.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserWrapperRepository extends JpaRepository<UserWrapper, Long> {

    Optional<UserWrapper> findUserWrapperByEmail(String email);

    // This query updates the status only for users whose due date has passed
    // and who aren't already marked as expired.
    @Modifying
    @Query("UPDATE UserWrapper u SET u.membershipStatus = :expiredStatus WHERE u.dueDate < :currentDate AND u.membershipStatus != :expiredStatus")
    int expirePastDueMemberships(@Param("expiredStatus") MembershipStatus expiredStatus, @Param("currentDate") LocalDate currentDate);
}
