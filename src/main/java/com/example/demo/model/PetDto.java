package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PetDto(
        Long id,
        Long userId,
        @NotBlank
        @Size(max = 30)
        @NotNull
        String name
) {
}
