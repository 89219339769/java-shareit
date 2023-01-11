package ru.practicum.shareit.exceptions;

public class EmailWrongException extends RuntimeException {
    public EmailWrongException(String message) {
        super(message);
    }
}