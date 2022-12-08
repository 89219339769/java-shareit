package ru.practicum.shareit.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exceptions.EmailWrongException;
import ru.practicum.shareit.exceptions.IncorrectEmailException;
import ru.practicum.shareit.exceptions.NoEmailException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse objectWrongEnterExeption(final NoEmailException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse objectWrongEnterExeption(final EmailWrongException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse objectWrongEnterExeption(final IncorrectEmailException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus. NOT_FOUND)
    public ErrorResponse objectWrongEnterExeption(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
