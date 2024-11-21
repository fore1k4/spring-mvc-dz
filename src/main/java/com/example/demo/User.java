package com.example.demo;

import java.util.List;

public record User(
        Long id,
        String name,
        String email,
        Integer age,
        List<Pet> pets

) {
}
