package com.example.demo.Service;

import com.example.demo.Pet;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PetService {
    private AtomicLong petId;
    private Long userId;
    private final UserService userService;

    public PetService(
            UserService userService
    ) {
        petId = new AtomicLong();
        this.userService = userService;
    }

    public Pet createPet(
            Pet petToCreate
    ) {
        var createdPet = new Pet(
                petId.incrementAndGet(),
                petToCreate.userId(),
                petToCreate.name()
        );

        userService.getUserById(petToCreate.userId())
                .pets().add(createdPet);

        return createdPet;
    }

    public Pet updatePet(
            Long id,
            Pet pet
    ) {
        var foundPet = findPetById(id);

        var updatedPet = new Pet(
                foundPet.get().id(),
                foundPet.get().userId(),
                pet.name()
        );

        var user = userService.getUserById(pet.userId());

        deletePet(pet.id());
        user.pets().add(updatedPet);

        return updatedPet;
    }

    public void deletePet(
            Long petId
    ) {
        var petToDelete = findPetById(petId)
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(petId)));;

        var user = userService.getUserById(petToDelete.userId());

        user.pets().remove(petToDelete);

    }

    public Optional<Pet> findPetById(
            Long id
    ) {
        return Optional.ofNullable(userService.getAllUsers().stream()
                .flatMap(user -> user.pets().stream())
                .filter(pet -> pet.id().equals(id))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Pet with id = %s, not found"
                        .formatted(id))));
    }

    public List<Pet> getAllPets() {
        return userService.getAllUsers().stream()
                .flatMap(user -> user.pets().stream())
                .toList();
    }


}
