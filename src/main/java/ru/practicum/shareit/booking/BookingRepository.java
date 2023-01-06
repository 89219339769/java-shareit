package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerIdOrderByStartDesc(Long userId);

    List<Booking> findAllByBookerAndStartBeforeAndEndAfter(User booker, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerAndEndBefore(User booker, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStartDesc(Long userId, LocalDateTime start);

    List<Booking> findAllByBookerAndStatusEquals(User booker, BookingStatus status);

    @Query("select b from Booking b Inner join Item i on b.item.id = i.id where i.owner.id = ?1 " +
            "order by b.start desc")
    Page<Booking> getAllUsersItemsBookings(Long userId, Pageable pageable);

    @Query("select b from Booking b Inner join Item i on b.item.id = i.id where i.owner.id = ?1 " +
            "order by b.start desc")
   List<Booking> getAllUsersItemsBookings1(Long userId);


    @Query("select b from Booking b Inner join Item i on b.item.id = i.id where i.owner.id = ?1 " +
            "and b.start > ?2 order by b.start desc")
    List<Booking> getFutureUsersItemsBookings(Long userId, LocalDateTime nowTime);

    List<Booking> findAllByItemOwnerAndEndBefore(User owner, LocalDateTime end);

    List<Booking> findAllByItemOwnerAndStartBeforeAndEndAfter(User owner, LocalDateTime start,
                                                              LocalDateTime end);

    List<Booking> findAllByItemOwnerAndStatusEquals(User owner, BookingStatus status);

    @Query(value = "select * " +
            "from bookings as b join items i on i.id = b.item_id " +
            "where i.id = ?1 and b.end_date < ?2 " +
            "order by b.end_date desc " +
            "limit 1 ", nativeQuery = true)
    Optional<Booking> findLastBookingByItem(long itemId, LocalDateTime time);

    @Query(value = "select * " +
            "from bookings as b join items i on i.id = b.item_id " +
            "where i.id = ?1 and b.start_date > ?2 " +
            "order by b.start_date desc " +
            "limit 1", nativeQuery = true)
    Optional<Booking> findNextBookingByItem(long itemId, LocalDateTime time);

    Optional<Booking> findBookingByItemIdAndBookerIdAndEndIsBefore(long itemId, long userId, LocalDateTime time);




    @Query(value = "select  b.ID ,\n" +
            "           b.START_DATE,\n" +
            "           b.END_DATE,\n" +
            "           b.BOOKER_ID as ddd,\n" +
            "b.STATUS as s,\n" +
            "b.ITEM_ID as bIID,\n" +
            "i.NAME,\n" +
            "i.DESCRIPTION,\n" +
            "i.IS_AVAILABLE,\n" +
            "i.ID_OWNER as iidO,\n" +
            "left join Items  i on b.item_id   = i.id   " +
            "where i.id in ?1" +
            " and b.status = 'APPROVED' " +
            "order by b.START_DATE",
            nativeQuery = true)
    List<Booking> findBookingsByItemsId(List<Long> itemsIdList);









}
