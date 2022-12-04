package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepository {
    private final Map<Long, List<Item>> items = new HashMap<>();


    public Item save(Item item) {
        item.setItemId(getId());
        items.compute(item.getUserId(), (userId, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });

        return item;
    }


    public Item findItemById(Long id) {

        //сначала взять все значения из мапы  в список
        List<List<Item>> temp = items.values().parallelStream().collect(Collectors.toList());

        for (long i = 0; i < temp.size(); i++) {

            List<Item> temp2 = temp.get((int) i);

            for (long j = 0; j < temp2.size(); j++) {

                if (temp2.get((int) j).getItemId().equals(id)) {
                    Item item = temp2.get(Math.toIntExact(id-1));
                    return item;
                }
            }
        }
        return null;
    }


    private long getId() {
        long lastId = items.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToLong(Item::getItemId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
