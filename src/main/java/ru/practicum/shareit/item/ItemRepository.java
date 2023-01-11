package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(" select i from Item i " +
            "where i.available = true and upper(i.name) like upper(concat('%', ?1, '%')) " +
            "or  i.available = true and upper(i.description) like upper(concat('%', ?1, '%')) " +
            "order by i.id asc ")
    List<Item> search(String text);


    Collection<Item> findAllByOwnerIdIsOrderById(Long userId);

    @Query(" select i from Item i " +
            "where i.requestId <> 0" +
            "order by i.id asc ")
    Collection<Item> findAllwithRequestId();


}
