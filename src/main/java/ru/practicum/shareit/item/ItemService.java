package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final
    Validator validator;


    public Item save(long userId, Item item) {
        item.setUserId(userId);
        validator.validateItemEmptyDescription(item);
        validator.validateUserNotFound(userId);
        validator.validateItemEmptyName(item);
        validator.validateItemWithOutEvailable(item);
        return itemRepository.save(item);
    }

    public Item updateItem(Long itemId, Long userId, Item item) {
        item.setUserId(userId);
        item.setId(itemId);

        if (!itemRepository.getItems().containsKey(userId)) {
            throw new NotFoundException("невозможно обновить, пользователя с этим номером не существует ");
        }

        List<Item> clientItems = itemRepository.getItems().get(userId);
        for (int k = 0; k < clientItems.size(); k++) {
            if (clientItems.get(k).getId().equals(item.getId())) {
                Item uptadeItem = clientItems.get(k);
                if (item.getName() != null && item.getName() != uptadeItem.getName()) {
                    uptadeItem.setName(item.getName());
                }
                if (item.getAvailable() != null && item.getAvailable() != uptadeItem.getAvailable()) {
                    uptadeItem.setAvailable(item.getAvailable());
                }
                if (item.getDescription() != null && item.getDescription() != uptadeItem.getDescription()) {
                    uptadeItem.setDescription(item.getDescription());
                }
                return uptadeItem;
            }
        }
        throw new NotFoundException("невозможно обновить, т.к. итема с этим номером не существует ");
    }

    public Item findItemById(Long id) {
        return itemRepository.findItemById(id);
    }

    public List<Item> findItemsByUserId(Long userId) {
        return itemRepository.findItemsByUser(userId);
    }

    public List<Item> findItemByNameOrDescription(String query) {
        return itemRepository.findItemByNameOrDescription(query);
    }
}

