package com.example.demo.Controller;

import com.example.demo.Converter.UserConverter;
import com.example.demo.Service.UserService;
import com.example.demo.User;
import com.example.demo.model.UserDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserConverter userConverter;

    public UserController(
            UserService userService,
            UserConverter userConverter
    ) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
           @RequestBody @Valid UserDto userDto
    ) {
        var savedUser = userService.createUser(userConverter.toUser(userDto));

        return ResponseEntity.ok(userConverter.toUserDto(savedUser));
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody @Valid UserDto userDto
    ) {
        var userToUpdate = new User(
                id,
                userDto.name(),
                userDto.email(),
                userDto.age(),
                userDto.pets().stream().toList()
        );
        var updatedUser = userService.updateUser(id,userToUpdate);

        return ResponseEntity.ok(userConverter.toUserDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
          @PathVariable("id") @Valid Long id
    ) {
       userService.deleteUser(id);
       return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public User getUserById(
          @PathVariable("id") @Valid Long id
    ) {
       return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers () {
        return userService.getAllUsers();
    }

}
