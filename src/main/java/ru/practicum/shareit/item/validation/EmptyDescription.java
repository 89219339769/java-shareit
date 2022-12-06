package ru.practicum.shareit.item.validation;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NoItemNameException;
import ru.practicum.shareit.item.model.Item;



@Component
    public class EmptyDescription {

        public void validate(Item item) {
            if (item.getDescription() == null) {
                throw new NoItemNameException("нужно указать описание");
            }
        }
    }


