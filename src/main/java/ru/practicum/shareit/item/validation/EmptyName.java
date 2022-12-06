package ru.practicum.shareit.item.validation;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NoAvailableException;
import ru.practicum.shareit.exceptions.NoItemNameException;
import ru.practicum.shareit.item.model.Item;
@Component
public class EmptyName {

    public void validate(Item item) {
        if (item.getName().isBlank()) {
            throw new NoItemNameException("нужно указать имя");
        }
    }
}
