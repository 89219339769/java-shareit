package ru.practicum.shareit.item;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.EmailWrongException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.*;
import java.util.stream.Collectors;

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
//


        List<List<Item>> list = new ArrayList<List<Item>>(items.values());


        for (int i = 0; i < list.size(); i++) {
            List<Item> listItem = new ArrayList<>();
            listItem = list.get(i);
            for (int j = 0; j < listItem.size(); j++) {
                if (listItem.get(i).getId() == itemId) {
                    Item item = listItem.get(i);
                    return item;
                }
            }
        }
        throw new NotFoundException("предмета  с этим номером не существует ");

    }
//            try {
//                List<Item> clientItems =  items.get(userId);
//                for (int k = 0; k < clientItems.size(); k++) {
//                    if (clientItems.get(k).getId() == itemId) {
//
//                        Item item = clientItems.get(k);
//                        return item;
//                    }
//                }
//
//            } catch (RuntimeException e) {
//                throw new NotFoundException("пользователя с  номером"+ userId+" не найдено ");
//            }
//            throw new NotFoundException("итема с номером "+itemId+ "не найдено");


//        //сначала взять все значения из мапы  в список
//        List<List<Item>> temp = items.values().parallelStream().collect(Collectors.toList());
//
//        for (long i = 0; i < temp.size(); i++) {
//
//            List<Item> temp2 = temp.get((int) i);
//
//            for (long j = 0; j < temp2.size(); j++) {
//
//                if (temp2.get((int) j).getId().equals(id)) {
//                    Item item = temp2.get(Math.toIntExact(id - 1));
//                    return item;
//                }
//            }
//        }
//        return null;





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
