package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.CreateUserWrapperDto;
import mk.ukim.finki.elibrary.server.dto.DisplayUserWrapperDto;

import java.util.List;
import java.util.Optional;

public interface UserWrapperApplicationService {

    DisplayUserWrapperDto adduser(CreateUserWrapperDto userDto);

    List<DisplayUserWrapperDto> getAllUsers();

    Optional<DisplayUserWrapperDto> updateUserInformation(Long userId, CreateUserWrapperDto userDto);

    Optional<DisplayUserWrapperDto> deleteUser(Long userId);

    Optional<DisplayUserWrapperDto> renewMembership(Long userId);

    Optional<DisplayUserWrapperDto> cancelMembership(Long userId);

    Optional<DisplayUserWrapperDto> getUserById(Long userId);


}
