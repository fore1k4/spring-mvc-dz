package com.example.demo.Converter;

import com.example.demo.Pet;
import com.example.demo.User;
import com.example.demo.model.PetDto;
import com.example.demo.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PetConventer {
    public PetDto toPetDto (Pet pet) {
        return new PetDto(
                pet.id(),
                pet.userId(),
                pet.name()
        );
    }

    public Pet toPet (PetDto petDto) {
        return new Pet(
                petDto.id(),
                petDto.userId(),
                petDto.name()
        );
    }
}
