package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerErrorDto> notValidException(
            MethodArgumentNotValidException e
    ) {
        String detailedMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

       var errorDto = new ServerErrorDto(
               "Ошибка валидации",
               detailedMessage,
               LocalDateTime.now()
       );
       return ResponseEntity.status(HttpStatus.BAD_REQUEST)
               .body(errorDto);
    }
    @ExceptionHandler
    public ResponseEntity<ServerErrorDto> gotAnyException(
            Exception e
    ) {
        var errorDto = new ServerErrorDto(
                "Ошибка сервера",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ServerErrorDto> notFoundException(
            NoSuchElementException e
    ) {
        var errorDto = new ServerErrorDto(
                "Сущность не найдена",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }
}
