package com.example.demo.Converter;

import com.example.demo.User;
import com.example.demo.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto toUserDto (User user) {
        return new UserDto(
                user.id(),
                user.name(),
                user.email(),
                user.age(),
                user.pets()
        );
    }

    public User toUser(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                userDto.age(),
                userDto.pets()
        );
    }

}
