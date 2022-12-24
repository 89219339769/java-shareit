package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoForOwner;
import ru.practicum.shareit.item.model.ItemDtoShort;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository repository;
    private final BookingRepository bookingRepository;
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
        if (!ItemExist) {
            throw new NotFoundException("Невозможно обновить вещь - " +
                    "у пользователя с id: " + userId + "нет такой вещи");
        }

        Item uptadeItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Вещи с номером - " + itemId +
                " не существует"));

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


//    public ItemDtoShort findItemById(Long id) {
//        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Вещи с номером - " + id +
//                " не существует"));
//        ItemDtoShort temDtoShort = itemMapper.itemToItemShort(item);
//        return temDtoShort;
//    }


    @Override
    public ItemDtoForOwner findItemById(Long id, Long ownerId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найдена вещь с id: " + id));
        //       ItemDtoShort temDtoShort = itemMapper.itemToItemShort(item);
//        temDtoShort.setComments(commentRepository.findAllByItemId(id)
//                .stream()
//                .map(CommentMapper::toCommentDto)
//                .collect(Collectors.toList()));
//        if (item.getOwner().getId().equals(ownerId)) {
//            setFieldsToItemDto(itemDto);
//        }
        Optional<Booking> lastBooking = bookingRepository.findLastBookingByItem(id, LocalDateTime.now());
        Optional<Booking> nextBooking = bookingRepository.findNextBookingByItem(id, LocalDateTime.now());
        Booking last;
        Booking next;
        if (lastBooking.isEmpty()) {
            last = null;
        } else {
            last = lastBooking.get();
        }
        if (nextBooking.isEmpty()) {
            next = null;
        } else {
            next = nextBooking.get();
        }
        if (item.getOwner().getId() == ownerId) {
            ItemDtoForOwner itemToItemDtoForOwner = itemMapper.itemToItemDtoForOwner(item);

            if (last != null) {
                itemToItemDtoForOwner.setStartFuture(last.getStart());
                itemToItemDtoForOwner.setEndFuture(last.getEnd());
            }

            if (next != null) {
                itemToItemDtoForOwner.setStartFuture(next.getStart());
                itemToItemDtoForOwner.setEndFuture(next.getEnd());
            }
            return itemToItemDtoForOwner;
        }
        ItemDtoForOwner itemToItemDtoForOwner = itemMapper.itemToItemDtoForOwner(item);
        return itemToItemDtoForOwner;
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
        if (itemExist) return itemsByUserId;
        else {
            throw new NotFoundException(" Не найдены вещи у пользователя с номером " + userId);
        }
    }


    public List<ItemDtoShort> findItemByNameOrDescription(String query) {
        if (query.isBlank()) {
            List<ItemDtoShort> itemsShort = new ArrayList<>();
            return itemsShort;
        }
        List<Item> items = itemRepository.search(query);
        return items.stream()
                .map(element -> itemMapper.itemToItemShort(element))
                .collect(Collectors.toList());
    }
}


