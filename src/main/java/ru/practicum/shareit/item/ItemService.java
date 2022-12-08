package ru.practicum.shareit.item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.validation.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserNotFound userNotFound;
    private final List<ItemValidation> validations;

    public Item save(long userId, Item item) {
        item.setUserId(userId);
        userNotFound.validate(userId);
        validations.stream().forEach(validator -> validator.validate(item));
        return itemRepository.save(item);
    }

    public Item updateItem(Long itemId, Long userId, Item item){
        item.setUserId(userId);
        item.setId(itemId);
        try{
            List<Item> clientItems = itemRepository.getItems().get(userId);
            for(int k = 0; k < clientItems.size(); k++){
                if(clientItems.get(k).getId() == item.getId()){
                    Item uptadeItem = clientItems.get(k);
                    if(item.getName() != null & item.getName() != uptadeItem.getName()){
                        uptadeItem.setName(item.getName());
                    }
                    if(item.getAvailable() != null & item.getAvailable() != uptadeItem.getAvailable()) {
                        uptadeItem.setAvailable(item.getAvailable());
                    }
                    if(item.getDescription() != null & item.getDescription() != uptadeItem.getDescription()){
                        uptadeItem.setDescription(item.getDescription());
                    }
                    return uptadeItem;
                }
            }

        } catch (RuntimeException e){
            throw new NotFoundException("невозможно обновить, пользователя с этим номером не существует ");
        }
        throw new NotFoundException("невозможно обновить, т.к. пользователя с этим номером не существует ");
    }

    public Item findItemById(Long id) {return itemRepository.findItemById(id);}

    public List<Item> findItemsByUserId(Long userId){
        return  itemRepository.findItemsByUser(userId);
    }

    public List<Item> findItemByName( String query) {return itemRepository.findItemByName(query);
    }
}

