package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

//    @Query(" select i from Item i " +
//            "where i.available = true and upper(i.name) like upper(concat('%', ?1, '%')) " +
//            "or  i.available = true and upper(i.description) like upper(concat('%', ?1, '%')) " +
//            "order by i.id asc ")
//    List<Item> search(String text);





    @Query(" select r from Request  r " +
            "where r.requestor.id = ?1 " +
            "order by r.created desc ")
    List<Request> getAllByUserId(Long UserId);




}
