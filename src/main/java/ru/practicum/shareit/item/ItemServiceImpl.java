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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository repository;
    private final BookingRepository bookingRepository;
    private final ItemMapper itemMapper;
    private final CommentRepository commentRepository;
    private final Validator validator;
    private final CommentMapper commentMapper;

    public ItemDtoShort save(long userId, Item item) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                        "не найден пользователь с id: " + userId));
        item.setOwner(user);
        validator.validateItemEmptyDescription(item);
        validator.validateItemEmptyName(item);
        validator.validateItemWithOutEvailable(item);
        itemRepository.save(item);
        ItemDtoShort temDtoShort = itemMapper.itemToItemShort(item);
        return temDtoShort;
    }

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
        if (Objects.equals(itemFromDb.get().getOwner().getId(), ownerId)) {
            Item itemFromDb1 = itemFromDb.get();
            ItemDtoForOwner itemDtoForOwner = itemMapper.toItemDtoForOwner(itemFromDb1, last, next, commentDtoOutList);
            return itemDtoForOwner;
        } else {
            ItemDtoForBooker itemDtoForBooker = ItemMapper.toItemDtoForBooker(itemFromDb.get(), commentDtoOutList);
            return itemDtoForBooker;
        }
    }








    public Collection<ItemDtoForOwner> getAllItems(long ownerId) {
        repository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать вещь - " +
                        "не найден пользователь с id: " + ownerId));

        Collection<ItemDtoForOwner> itemDtoForOwnersList = new ArrayList<>();
        Collection<Item> itemsList = itemRepository.findAllByOwnerIdIsOrderById(ownerId);

        List<Comment> commentListbyUser = commentRepository.getAllCommentsByUserId(ownerId);

        System.out.println("все упадет!!!!!!!!!!!!!!!!!!!!!!!" +
                "!!!!!!!!!!!!!!!!!!!!!!");

        List<Booking> bookingListbyUserOwner = new ArrayList<>();
        bookingListbyUserOwner = bookingRepository.getAllUsersItemsBookings(ownerId);




        for (Item item : itemsList) {
            // Collection<Comment> commentList = commentRepository.findAllByItemIdIs(item.getId());

            Collection<CommentDtoOut> commentDtoOutList = new ArrayList<>();

            commentDtoOutList = commentListbyUser.stream()
                    .filter(comment -> comment.getItem().equals(item.getId()))
                    .map(CommentMapper::toCommentDt0FromComment)
                    .collect(Collectors.toList());



//            itemRepository.findAllByOwnerIdIsOrderById(ownerId);
//            List<Long> itemsIdList = new ArrayList<>();
//            for (Item item3 : itemsList) {
//                itemsIdList.add(item3.getId());
//            }






                Booking lastBooking = bookingListbyUserOwner.stream()
                        .filter(booking -> booking.getItem().getId().equals(item.getId()) &&
                                booking.getEnd().isBefore(LocalDateTime.now()))
                         .sorted((o1, o2) -> {
                               int result = o1.getEnd().compareTo(o2.getEnd());
                                return result * -1;})
                        .reduce((a, b) -> b).orElse(null);
                //   .limit(1)
                // .collect(Collectors.toList());
                //   Booking lastBooking =  bookingListbyUserOwnerOneLast.get(0);
                //    .findFirst().orElse(null))
                // .collect(Collectors.toList());
                Booking nextBooking = bookingListbyUserOwner.stream()
                        .filter(booking -> booking.getItem().getId().equals(item.getId()) &&
                                booking.getStart().isAfter(LocalDateTime.now()))
                        .findFirst().orElse(null);
                //   .sorted((o1, o2) -> {
                //        int result = o1.getStart().compareTo(o2.getStart());
                //          return result * -1;})
                //      .limit(1)
                //      .collect(Collectors.toList());
                //   Booking nextBooking =  bookingListbyUserOwnerOneNext.get(0);
                //    lastBooking = bookingRepository.findLastBookingByItem(item.getId(), LocalDateTime.now());
                //      nextBooking = bookingRepository.findNextBookingByItem(item.getId(), LocalDateTime.now());
                //    Booking last;
                //      Booking next;
                //     if (lastBooking.isEmpty()) {
                //          last = null;
                //      } else {
                //           last = lastBooking.get();
                //       }
                //        if (nextBooking.isEmpty()) {
                //            next = null;
                //       } else {
//             next = nextBooking.get();
                //        }
                itemDtoForOwnersList.add(ItemMapper.toItemDtoForOwner(item, lastBooking, nextBooking, commentDtoOutList));
            }
            return itemDtoForOwnersList;
        }


//            lastBooking = bookingRepository.findLastBookingByItem(item.getId(), LocalDateTime.now());
//            nextBooking = bookingRepository.findNextBookingByItem(item.getId(), LocalDateTime.now());
//            Booking last;
//            Booking next;
//            if (lastBooking.isEmpty()) {
//                last = null;
//            } else {
//                last = lastBooking.get();
//            }
//            if (nextBooking.isEmpty()) {
//                next = null;
//            } else {
//                next = nextBooking.get();
//            }




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



  //  List<Comment> findAllByItemIdFromCommentListbyUser (Long itemId){


  //
   // }
}


