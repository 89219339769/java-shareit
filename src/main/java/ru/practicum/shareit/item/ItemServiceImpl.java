package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.comment.CommentDtoOut;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.booking.BookingRepository;

import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository repository;
    private final BookingRepository bookingRepository;
    private final ItemMapper itemMapper;
    private final CommentRepository commentRepository;

    private final RequestRepository requestRepository;
    private final Validator validator;
    private final CommentMapper commentMapper;

    @Override
    public ItemDtoShort save(long userId, Item item) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                        "не найден пользователь с id: " + userId));
        item.setOwner(user);
        validator.validateItemEmptyDescription(item);
        validator.validateItemEmptyName(item);
        validator.validateItemWithOutEvailable(item);


        if (item.getRequestId() != null) {
            requestRepository.findById(item.getRequestId())
                    .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                            "не найден апрос с id: " + item.getRequestId()));
        }

        itemRepository.save(item);

        return  itemMapper.itemToItemShort(item);

    }


    @Override
    public Item updateItem(Long itemId, Long userId, Item item) {
        User owner = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                        "не найден пользователь с id: " + userId));
        item.setOwner(owner);
        Item uptadeItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Вещи с номером - " + itemId +
                " не существует"));

        if (!Objects.equals(uptadeItem.getOwner().getId(), userId)) {
            throw new NotFoundException("Невозможно обновить вещь - " +
                    "у пользователя с id: " + userId + "нет такой вещи");
        }

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

            commentDtoOutList.add(CommentMapper.toCommentDt0FromComment(comment));
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
        if (Objects.equals(itemFromDb.get().getOwner().getId(), ownerId)) {
            Item itemFromDb1 = itemFromDb.get();
            ItemDtoForOwner itemDtoForOwner = itemMapper.toItemDtoForOwner(itemFromDb1, last, next, commentDtoOutList);
            return itemDtoForOwner;
        } else {
            ItemDtoForBooker itemDtoForBooker = ItemMapper.toItemDtoForBooker(itemFromDb.get(), commentDtoOutList);
            return itemDtoForBooker;
        }
    }

    @Override
    public Collection<ItemDtoForOwner> getAllItems(long ownerId) {
        repository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                        "не найден пользователь с id: " + ownerId));

        Collection<ItemDtoForOwner> itemDtoForOwnersList = new ArrayList<>();

        Collection<Item> itemsList = itemRepository.findAllByOwnerIdIsOrderById(ownerId);

        List<Comment> commentListbyUser = commentRepository.getAllCommentsByUserId(ownerId);

        List<Booking> bookingListbyUserOwner;
        bookingListbyUserOwner = bookingRepository.getAllUsersItemsBookingsList(ownerId);

        for (Item item : itemsList) {
            Collection<CommentDtoOut> commentDtoOutList;
            commentDtoOutList = commentListbyUser.stream()
                    .filter(comment -> comment.getItem().getId().equals(item.getId()))
                    .map(CommentMapper::toCommentDt0FromComment)
                    .collect(Collectors.toList());
            Booking lastBooking = bookingListbyUserOwner.stream()
                    .filter(booking -> booking.getItem().getId().equals(item.getId()) &&
                            booking.getEnd().isBefore(LocalDateTime.now()))
                    .sorted((o1, o2) -> {
                        int result = o1.getEnd().compareTo(o2.getEnd());
                        return result * -1;
                    })
                    .reduce((a, b) -> b).orElse(null);
            Booking nextBooking = bookingListbyUserOwner.stream()
                    .filter(booking -> booking.getItem().getId().equals(item.getId()) &&
                            booking.getStart().isAfter(LocalDateTime.now()))
                    .sorted((o2, o1) -> {
                        int result = o1.getStart().compareTo(o2.getStart());
                        return result * -1;
                    })
                    .findFirst().orElse(null);

            itemDtoForOwnersList.add(ItemMapper.toItemDtoForOwner(item, lastBooking, nextBooking, commentDtoOutList));
        }
        return itemDtoForOwnersList;
    }


    @Override
    public List<ItemDtoShort> findItemByNameOrDescription(String query) {
        if (query.isBlank()) {
            return  new ArrayList<>();
        }
        List<Item> items = itemRepository.search(query);
        return items.stream()
                .map(element -> itemMapper.itemToItemShort(element))
                .collect(Collectors.toList());
    }
}


