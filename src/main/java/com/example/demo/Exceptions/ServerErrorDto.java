package com.example.demo.Exceptions;

import java.time.LocalDateTime;

public record ServerErrorDto(
        String message,
        String detailedMessage,
        LocalDateTime time
) {
}
