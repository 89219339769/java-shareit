package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoShort;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository repository;

    private final ItemMapper itemMapper;
    private final
    Validator validator;


    public ItemDtoShort save(long userId, Item item) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                        "не найден пользователь с id: " + userId));

        item.setOwner(user);
        validator.validateItemEmptyDescription(item);
        validator.validateUserNotFound(userId);
        validator.validateItemEmptyName(item);
        validator.validateItemWithOutEvailable(item);
        itemRepository.save(item);
        ItemDtoShort temDtoShort = itemMapper.itemToItemShort(item);
        return temDtoShort;

    }


    public Item updateItem(Long itemId, Long userId, Item item) {
        boolean ItemExist = false;
        List<ItemDtoShort> items = findItemsByUserId(userId);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(itemId)) {
                ItemExist = true;
            }
        }
        if (ItemExist == false) {
            throw new NotFoundException("Невозможно обновить вещь - " +
                    "у пользователя с id: " + userId + "нет такой вещи");
        }

//        User user = repository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("Невозможно обновить вещь - " +
//                        "не найден пользователь с id: " + userId));
//        Item itemInDb = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Вещи с номером - " + itemId +
//                " не существует"));
//        User userItem = itemInDb.getOwner();
//
//
//        if(itemInDb.getOwner()!=(user)){
//            new NotFoundException("Невозможно обновить вещь - " +
//                    "у пользователя с id: " + userId + "нет такой вещи");

        //вещь 1 нету у пользователя 4 нужно проверить

        //  item.setOwner(user);
        //  item.setId(itemId);
        Item uptadeItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Вещи с номером - " + itemId +
                " не существует"));
//        List<Item> clientItems = itemRepository.findAll();
//        for (int k = 0; k < clientItems.size(); k++) {
//
        //           if (clientItems.get(k).equals(item)) {
//                Item uptadeItem = clientItems.get(k);
        if (item.getName() != null && item.getName() != uptadeItem.getName()) {
            uptadeItem.setName(item.getName());
        }

        if (item.getAvailable() != null && item.getAvailable() != uptadeItem.getAvailable()) {
            uptadeItem.setAvailable(item.getAvailable());
        }
        if (item.getDescription() != null && item.getDescription() != uptadeItem.getDescription()) {
            uptadeItem.setDescription(item.getDescription());
        }
        itemRepository.save(uptadeItem);
        return uptadeItem;
    }

//        throw new NotFoundException("невозможно обновить, т.к. итема с этим номером не существует ");
//    }
//


    public ItemDtoShort findItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Вещи с номером - " + id +
                " не существует"));
        ItemDtoShort temDtoShort = itemMapper.itemToItemShort(item);
        return temDtoShort;
    }


    public List<ItemDtoShort> findItemsByUserId(Long userId) {
        List<Item> allItems = itemRepository.findAll();
        List<ItemDtoShort> itemsByUserId = new ArrayList<>();
        boolean itemExist = false;
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getOwner().getId().equals(userId)) {
                itemsByUserId.add(itemMapper.itemToItemShort(allItems.get(i)));
                itemExist = true;
            }
        }
        if (itemExist == true) return itemsByUserId;
        else {
            throw new NotFoundException(" Не найдены вещи у пользователя с номером " + userId);
        }
    }
}
//
//    public List<Item> findItemByNameOrDescription(String query) {
//        return itemRepository.findItemByNameOrDescription(query);
//    }


