package rs.itbootcamp.ITBC_Logger.controllers;

import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.itbootcamp.ITBC_Logger.models.User;
import rs.itbootcamp.ITBC_Logger.models.enumerators.LogType;
import rs.itbootcamp.ITBC_Logger.models.enumerators.UserType;
import rs.itbootcamp.ITBC_Logger.repository.JpaUserRepository;
import rs.itbootcamp.ITBC_Logger.models.Log;
import rs.itbootcamp.ITBC_Logger.repository.UserRepository;

import java.util.UUID;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final JpaUserRepository jpaUserRepository;

    @Autowired
    public UserController(UserRepository userRepository, JpaUserRepository jpaUserRepository) {
        this.userRepository = userRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @PostMapping("/api/clients/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        if (user.getUsername().length() < 3 || !user.passwordValid(user) || !user.emailValid(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (jpaUserRepository.isDuplicateName(user.getUsername()) > 0 || jpaUserRepository.isDuplicateEmail(user.getEmail()) > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists.");

        }
        user.setId(UUID.randomUUID());
        userRepository.insertUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered.");
    }

    @PostMapping("/api/clients/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        if (jpaUserRepository.isDuplicateName(user.getUsername()) > 0) {
            String token = jpaUserRepository.getIdByName(user.getUsername()).toString();
            System.out.println(token);

            jpaUserRepository.saveToken(token, user.getUsername());
            System.out.println(user.getToken());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect credentials.");

    }

    @GetMapping("/api/clients")
    public ResponseEntity<?> getAllClients(@RequestHeader String token) {
        if (!token.equalsIgnoreCase(jpaUserRepository.getToken(token))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect token.");
        }
        if (jpaUserRepository.getUserTypeById(token).equalsIgnoreCase(UserType.USER.name())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not admin.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.getAllUsers());
    }

    @PatchMapping("/api/clients/{clientId}/reset-password")
    public ResponseEntity<?> resetPassword(@RequestHeader String token, @RequestBody User user, @PathVariable (value = "clientId") String id) {
        if (!token.equalsIgnoreCase(jpaUserRepository.getToken(token))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect token.");
        }
        if (jpaUserRepository.getUserTypeById(token).equalsIgnoreCase(UserType.USER.name())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not admin.");
        }
        String oldPassword = jpaUserRepository.getPasswordById(id);;
        jpaUserRepository.updatePassword(user.getPassword(), id);
        if (user.passwordValid(user)) {
            return ResponseEntity.status(HttpStatus.OK).body("password changed.");
        }
        jpaUserRepository.updatePassword(oldPassword, id);
        return ResponseEntity.status(HttpStatus.OK).body("password not valid.");
    }
}


