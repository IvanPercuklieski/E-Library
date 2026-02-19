package mk.ukim.finki.elibrary.server.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.elibrary.server.dto.CreateUserWrapperDto;
import mk.ukim.finki.elibrary.server.dto.DisplayUserWrapperDto;
import mk.ukim.finki.elibrary.server.model.exceptions.*;
import mk.ukim.finki.elibrary.server.service.backend.application.UserWrapperApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "UserWrapper API", description = "Endpoints for managing users.")
@RequestMapping("/api/user")
public class UserWrapperController {
    private final UserWrapperApplicationService userWrapperApplicationService;

    public UserWrapperController(UserWrapperApplicationService userWrapperApplicationService) {
        this.userWrapperApplicationService = userWrapperApplicationService;
    }

    @Operation(summary = "Get all users", description = "Get all users from the system")
    @GetMapping("/all")
    public ResponseEntity<List<DisplayUserWrapperDto>> getAllUsers() {
        return ResponseEntity.ok(userWrapperApplicationService.getAllUsers());
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id", description = "Get user information by id")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(userWrapperApplicationService.getUserById(userId));
        }catch (UserWrapperNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Add a genre", description = "Add user to the system. Start date from the membership is the current time and date, while the end is exactly one month from the start date.")
    public ResponseEntity<?> addUser(@RequestBody CreateUserWrapperDto userDto) {
        try{
            return ResponseEntity.ok(userWrapperApplicationService.adduser(userDto));
        } catch (UserWrapperEmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/update/{userId}")
    @Operation(summary = "Update user information", description = "Update user information")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                        @RequestBody CreateUserWrapperDto userDto) {

        try{
            return ResponseEntity.ok(userWrapperApplicationService.updateUserInformation(userId, userDto));
        } catch (UserWrapperNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "Delete a user", description = "Delete a user by their id, only if they have returned all of their books")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {

        try{
            return ResponseEntity.ok(userWrapperApplicationService.deleteUser(userId));
        } catch (UserWrapperNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (CannotRemoveUserException e2){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e2.getMessage());
        }
    }

    @PutMapping("/renew-membership-status/{userId}")
    @Operation(summary = "Renew membership status", description = "Renew membership status for a user. Only is the status is already EXPIRED or CANCELED")
    public ResponseEntity<?> renewMembership(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(userWrapperApplicationService.renewMembership(userId));
        }catch (MembershipCannotBeRenewedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/cancel-membership-status/{userId}")
    @Operation(summary = "Cancel membership status", description = "Cancel membership status for a user. Only is the status is already ACTIVE or EXPIRED")
    public ResponseEntity<?> cancelMembership(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(userWrapperApplicationService.cancelMembership(userId));
        }catch (MembershipIsAlreadyCancelledException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
