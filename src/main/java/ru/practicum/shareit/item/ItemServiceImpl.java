package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoShort;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository repository;

    private final ItemMapper itemMapper;
    private final
    Validator validator;


    public ItemDtoShort save(long userId, Item item) {

        repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                        "не найден пользователь с id: " + userId));
        item.setOwnerId(userId);
        validator.validateItemEmptyDescription(item);
        validator.validateUserNotFound(userId);
        validator.validateItemEmptyName(item);
        validator.validateItemWithOutEvailable(item);
        itemRepository.save(item);
        ItemDtoShort temDtoShort = itemMapper.itemToItemShort(item);
        return temDtoShort;

    }

    //
//    public Item updateItem(Long itemId, Long userId, Item item) {
//        item.setUserId(userId);
//        item.setId(itemId);
//
//        if (!itemRepository.getItems().containsKey(userId)) {
//            throw new NotFoundException("невозможно обновить, пользователя с этим номером не существует ");
//        }
//
//        List<Item> clientItems = itemRepository.getItems().get(userId);
//        for (int k = 0; k < clientItems.size(); k++) {
//            if (clientItems.get(k).getId().equals(item.getId())) {
//                Item uptadeItem = clientItems.get(k);
//                if (item.getName() != null && item.getName() != uptadeItem.getName()) {
//                    uptadeItem.setName(item.getName());
//                }
//                if (item.getAvailable() != null && item.getAvailable() != uptadeItem.getAvailable()) {
//                    uptadeItem.setAvailable(item.getAvailable());
//                }
//                if (item.getDescription() != null && item.getDescription() != uptadeItem.getDescription()) {
//                    uptadeItem.setDescription(item.getDescription());
//                }
//                return uptadeItem;
//            }
//        }
//        throw new NotFoundException("невозможно обновить, т.к. итема с этим номером не существует ");
//    }
//
    public ItemDtoShort findItemById(Long id) {
       Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Вещи с номером - " + id +
                " не существует"));
        ItemDtoShort temDtoShort = itemMapper.itemToItemShort(item);
        return temDtoShort;
    }
}
//
//    public List<Item> findItemsByUserId(Long userId) {
//        return itemRepository.findItemsByUser(userId);
//    }
//
//    public List<Item> findItemByNameOrDescription(String query) {
//        return itemRepository.findItemByNameOrDescription(query);
//    }


