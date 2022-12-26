package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.comment.*;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final Validator validator;

    private final CommentMapper commentMapper;


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


    @Override
    public ItemDtoAbstract findItemById(Long id, Long ownerId) {
        Optional<Item> itemFromDb = itemRepository.findById(id);
        if (itemFromDb.isEmpty()) {
            throw new NotFoundException("Вещи с ID = " + id + " не существует.");
        }

        Collection<Comment> commentList = commentRepository.findAllByItemIdIs(id);
        Collection<CommentDtoOut> commentDtoOutList = new ArrayList<>();
        for (Comment comment : commentList) {

            commentDtoOutList.add(commentMapper.toCommentDt0FromComment(comment));
        }


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
        if (itemFromDb.get().getOwner().getId() == ownerId) {

            Item itemFromDb1 = itemFromDb.get();


            ItemDtoForOwner itemDtoForOwner = itemMapper.toItemDtoForOwner(itemFromDb1, last, next, commentDtoOutList);


            return itemDtoForOwner;
        } else {

            ItemDtoForBooker itemDtoForBooker = ItemMapper.toItemDtoForBooker(itemFromDb.get(), commentDtoOutList);



            return itemDtoForBooker;
        }
    }


    public Collection<ItemDtoForOwner> getAllItems(long ownerId) {
        User user = repository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                        "не найден пользователь с id: " + ownerId));
        Optional<Booking> lastBooking;
        Optional<Booking> nextBooking;
        Collection<ItemDtoForOwner> itemDtoForOwnersList = new ArrayList<>();
        Collection<Item> itemsList = itemRepository.findAllByOwnerIdIsOrderById(ownerId);
        for (Item item : itemsList) {
            Collection<Comment> commentList = commentRepository.findAllByItemIdIs(item.getId());
            Collection<CommentDtoOut> commentDtoOutList = new ArrayList<>();
            for (Comment comment : commentList) {
              //  User author = validateUser(comment.getAuthorID());
                commentDtoOutList.add(commentMapper.toCommentDt0FromComment(comment));
            }
            lastBooking = bookingRepository.findLastBookingByItem(item.getId(), LocalDateTime.now());
            nextBooking = bookingRepository.findNextBookingByItem(item.getId(), LocalDateTime.now());
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
            itemDtoForOwnersList.add(ItemMapper.toItemDtoForOwner(item, last, next, commentDtoOutList));
        }
        return itemDtoForOwnersList;





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





}


