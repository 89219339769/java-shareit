package ru.practicum.shareit.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.practicum.shareit.exceptions.EmptyItemDescriptionException;
import ru.practicum.shareit.exceptions.NoAvailableException;
import ru.practicum.shareit.exceptions.NoItemNameException;
import ru.practicum.shareit.user.model.ErrorResponse;

@RestControllerAdvice
public class ItemErrorHendler {


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse objectWrongEnterExeption(final NoAvailableException e) {
        return new ErrorResponse(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse objectWrongEnterExeption(final NoItemNameException e) {
        return new ErrorResponse(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse objectWrongEnterExeption(final EmptyItemDescriptionException e) {
        return new ErrorResponse(e.getMessage());
    }
}
