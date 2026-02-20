package mk.ukim.finki.elibrary.server.service.domain.impl;


import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.enumerations.MembershipStatus;
import mk.ukim.finki.elibrary.server.model.exceptions.*;
import mk.ukim.finki.elibrary.server.repository.EmployeeRepository;
import mk.ukim.finki.elibrary.server.repository.SeatRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.domain.UserWrapperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserWrapperServiceImpl implements UserWrapperService {

    private final UserWrapperRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final SeatRepository seatRepository;


    static final int MEMBERSHIP_PERIOD = 1;

    public UserWrapperServiceImpl(UserWrapperRepository userRepository, EmployeeRepository employeeRepository, SeatRepository seatRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.seatRepository = seatRepository;
    }


    @Override
    public UserWrapper createUser(UserWrapper userWrapper) {

        String email = userWrapper.getEmail();
        if (userRepository.findUserWrapperByEmail(email) != null) {
            throw new UserWrapperEmailAlreadyExistsException(email);
        }

        // the membership lasts for one month
        // each month the customer needs to pay at the library
        LocalDate fromDate = LocalDate.now();
        LocalDate dueDate = fromDate.plusMonths(MEMBERSHIP_PERIOD);

        UserWrapper user = new UserWrapper(userWrapper.getName(), userWrapper.getSurname(), fromDate, dueDate, userWrapper.getEmail());
        return userRepository.save(user);
    }

    @Override
    public List<UserWrapper> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserWrapper> findById(Long userId) {
        return Optional.of(userRepository.findById(userId).orElseThrow(() -> new UserWrapperNotFoundException(userId)));
    }

    @Override
    public Optional<UserWrapper> updateUserInformation(Long userId, UserWrapper userWrapper) {
        UserWrapper user = findById(userId).orElseThrow(() -> new UserWrapperNotFoundException(userId));

        user.setName(userWrapper.getName());
        user.setSurname(userWrapper.getSurname());
        user.setEmail(userWrapper.getEmail());

        return Optional.of(userRepository.save(user));
    }

    /**
     * Brishenje na user, no samo ako ne dolzi vekje knigi ili nema rez sedishte
     * @param userId
     * @return
     */

    @Override
    @Transactional
    public Optional<UserWrapper> deleteUser(Long userId) {
        UserWrapper user = findById(userId).orElseThrow(() -> new UserWrapperNotFoundException(userId));

        if(!user.getBorrowedBooks().isEmpty() || seatRepository.existsSeatByUser_Id(userId))
            throw new CannotRemoveUserException(userId);

        employeeRepository.deleteEmployeeByUser_Id(userId);

        userRepository.delete(user);
        return Optional.of(user);
    }

    /**
     * Obnova na membership za broj na mececi MEMBERSHIP_PERIOD
     * OBNOVATA VAZI SAMO ZA:
     *  <li>ako e vekje istecen rokot</li>
     *  <li> ako statusot e CANCELED i saka pak da se zacleni</li>
     * @param userId
     * @return
     */
    @Override
    public Optional<UserWrapper> renewMembership(Long userId) {
        UserWrapper user = findById(userId).orElseThrow(() -> new UserWrapperNotFoundException(userId));

        if (user.getMembershipStatus() == MembershipStatus.EXPIRED || user.getMembershipStatus() == MembershipStatus.CANCELLED) {
            user.setDueDate(user.getDueDate().plusMonths(MEMBERSHIP_PERIOD));
            user.setMembershipStatus(MembershipStatus.ACTIVE);
            return Optional.of(userRepository.save(user));
        } else {
            throw new MembershipCannotBeRenewedException(user.getName(), user.getSurname());
        }
    }

    @Override
    public Optional<UserWrapper> cancelMembership(Long userId) {
        UserWrapper user = findById(userId).orElseThrow(() -> new UserWrapperNotFoundException(userId));

        if(user.getMembershipStatus() != MembershipStatus.CANCELLED) {
            user.setMembershipStatus(MembershipStatus.CANCELLED);
            return Optional.of(userRepository.save(user));
        } else {
            throw new MembershipIsAlreadyCancelledException(userId);
        }
    }
}
