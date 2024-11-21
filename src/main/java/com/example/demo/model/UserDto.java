package com.example.demo.model;

import com.example.demo.Pet;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        @Null
        Long id,
        @NotBlank
        @Size(max = 30)
        @NotNull
        String name,
        @Email
        @NotBlank
        @NotNull
        String email,

        @Min(0)
        @Max(120)
        @NotNull
        Integer age,
        List<Pet> pets
) {
}
