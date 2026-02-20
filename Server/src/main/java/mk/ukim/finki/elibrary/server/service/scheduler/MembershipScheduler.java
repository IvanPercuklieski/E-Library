package mk.ukim.finki.elibrary.server.service.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.elibrary.server.model.enumerations.MembershipStatus;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j // lombok annotation for logging
public class MembershipScheduler {

    private final UserWrapperRepository userWrapperRepository;

    // Runs every day at midnight (00:00:00)
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional // Required because we are executing a @Modifying query
    public void checkAndExpireMemberships() {
        log.info("Starting scheduled job: Checking for expired memberships...");

        LocalDate today = LocalDate.now();

        // Execute the bulk update
        int updatedCount = userWrapperRepository.expirePastDueMemberships(MembershipStatus.EXPIRED, today);

        log.info("Finished scheduled job: Expired {} memberships.", updatedCount);
    }
}
