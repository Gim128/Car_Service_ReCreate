package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.UserDTO;
import lk.carservice.demo.dto.UserResponseDTO;
import lk.carservice.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserResponseDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false) Integer userType,
            @RequestParam(required = false) Boolean active) {

        if (userType != null) {
            return ResponseEntity.ok(userService.getUsersByType(userType));
        } else if (active != null) {
            return ResponseEntity.ok(active ?
                    userService.getActiveUsers() :
                    userService.getAllUsers().stream()
                            .filter(u -> !u.getIsActive())
                            .collect(Collectors.toList()));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Integer userId,
            @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, userDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/activate")
    public ResponseEntity<UserResponseDTO> activateUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @PostMapping("/{userId}/deactivate")
    public ResponseEntity<UserResponseDTO> deactivateUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.deactivateUser(userId));
    }
}
