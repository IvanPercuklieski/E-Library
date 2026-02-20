package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.CreateUserWrapperDto;
import mk.ukim.finki.elibrary.server.dto.DisplayUserWrapperDto;
import mk.ukim.finki.elibrary.server.service.backend.application.UserWrapperApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.UserWrapperService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserWrapperApplicationServiceImpl implements UserWrapperApplicationService {

    private final UserWrapperService userWrapperService;

    public UserWrapperApplicationServiceImpl(UserWrapperService userWrapperService) {
        this.userWrapperService = userWrapperService;
    }

    @Override
    public DisplayUserWrapperDto adduser(CreateUserWrapperDto userDto) {
        return DisplayUserWrapperDto.from(userWrapperService.createUser(userDto.toUserWrapper()));
    }

    @Override
    public List<DisplayUserWrapperDto> getAllUsers() {
        return DisplayUserWrapperDto.from(userWrapperService.findAll());
    }

    @Override
    public Optional<DisplayUserWrapperDto> updateUserInformation(Long userId, CreateUserWrapperDto userDto) {
        return userWrapperService.updateUserInformation(userId, userDto.toUserWrapper())
                .map(DisplayUserWrapperDto::from);
    }

    @Override
    public Optional<DisplayUserWrapperDto> deleteUser(Long userId) {
        return userWrapperService.deleteUser(userId)
                .map(DisplayUserWrapperDto::from);
    }

    @Override
    public Optional<DisplayUserWrapperDto> renewMembership(Long userId) {
        return userWrapperService.renewMembership(userId)
                .map(DisplayUserWrapperDto::from);
    }

    @Override
    public Optional<DisplayUserWrapperDto> cancelMembership(Long userId) {
        return userWrapperService.cancelMembership(userId)
                .map(DisplayUserWrapperDto::from);
    }

    @Override
    public Optional<DisplayUserWrapperDto> getUserById(Long userId) {
        return userWrapperService.findById(userId)
                .map(DisplayUserWrapperDto::from);
    }
}
