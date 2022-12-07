package ru.practicum.shareit.item;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import java.util.*;


@Data
@Component
@RequiredArgsConstructor
public class ItemRepository {
    private final Map<Long, List<Item>> items = new HashMap<>();
    private final UserRepository repository;

    public Item save(Item item) {
        item.setId(getId());
        items.compute(item.getUserId(), (userId, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });
        return item;
    }

    public Item findItemById(Long itemId) {

        List<List<Item>> list = new ArrayList<>(items.values());
        for (int i = 0; i < list.size(); i++) {
            List<Item> listItem = list.get(i);
            for (int j = 0; j < listItem.size(); j++) {
                if (listItem.get(i).getId() == itemId) {
                    Item item = listItem.get(i);
                    return item;
                }
            }
        }
        throw new NotFoundException("предмета  с этим номером не существует ");
    }

    public List<Item> findItemsByUser(Long userId){
       try {
           List<Item> item = items.get(userId);
           return item;
       }
       catch (RuntimeException e){
           throw new NotFoundException(" Пользователь не найден");
       }
    }

    private long getId() {
        long lastId = items.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
