package ru.practicum.shareit.item.validation;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NoAvailableException;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemWithoutAvailable {


    public void validate(Item item) {
        if (item.getAvailable() == null) {
            throw new NoAvailableException("нужно указать состояние");
        }
    }
}
