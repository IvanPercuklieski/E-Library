package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

import java.util.List;
import java.util.Optional;

public interface UserWrapperService {

    UserWrapper createUser(UserWrapper userWrapper);

    List<UserWrapper> findAll();

    Optional<UserWrapper> findById(Long userId);

    // update user, da si smeni userot podatoci za sebe
    Optional<UserWrapper> updateUserInformation(Long userId, UserWrapper userWrapper);

    Optional<UserWrapper> deleteUser(Long userId);

    Optional<UserWrapper> renewMembership(Long userId);

    Optional<UserWrapper> cancelMembership(Long userId);
}
