package com.example.demo.Service;

import com.example.demo.User;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final AtomicLong idCounter;
    private Map<Long, User> userMap;

    public UserService() {
        idCounter = new AtomicLong();
        userMap = new HashMap<>();
    }

    public User createUser(
         User userToCreate
    ) {
        Long newId = idCounter.incrementAndGet();
        var user = new User(
                newId,
                userToCreate.name(),
                userToCreate.email(),
                userToCreate.age(),
                new ArrayList<>()
        );
        userMap.put(newId,user);
        return user;
    }

    public User updateUser(
            Long id,
            User userToUpdate
    ) {
        if(!userMap.containsKey(id)) {
            throw new NoSuchElementException("No such user with id = %s".formatted(id));
        }

        var updatedUser = new User(
                id,
                userToUpdate.name(),
                userToUpdate.email(),
                userToUpdate.age(),
                userToUpdate.pets()
        );
        userMap.put(id,updatedUser);
        return updatedUser;
    }

    public void deleteUser(
            Long id
    ) {
        if(!userMap.containsKey(id)) {
            throw new NoSuchElementException("No such user with id = %s".formatted(id));
        }
        userMap.remove(id);
    }

    public User getUserById(
            Long id
    ) {
        if(!userMap.containsKey(id)) {
            throw new NoSuchElementException("No such user with id = %s".formatted(id));
        }
        return Optional.ofNullable(userMap.get(id))
                .orElseThrow(() -> new RuntimeException("user with id = %s, not found"
                        .formatted(id)));
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }
}
