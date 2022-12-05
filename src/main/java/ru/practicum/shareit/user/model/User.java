package ru.practicum.shareit.user.model;

/**
 * TODO Sprint add-controllers.
 */
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
}
