package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.awt.print.Pageable;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

//    @Query(" select i from Item i " +
//            "where i.available = true and upper(i.name) like upper(concat('%', ?1, '%')) " +
//            "or  i.available = true and upper(i.description) like upper(concat('%', ?1, '%')) " +
//            "order by i.id asc ")
//    List<Item> search(String text);




    List<Request> findAllByRequestorIdOrderByCreatedAsc(Long userId);

    @Query(" select r from Request  r " +
            "join Item i on i.requestId = r.id " +
            "where r.requestor.id <> ?1  and i.requestId <> 0"+
            "order by r.created desc ")
    List<Request> getAllByUserIdNot(Long UserId, PageRequest pageRequest);

    @Query(" select r from Request  r " +
            "where r.requestor.id = ?1 " +
            "order by r.created desc ")
   List<Request> getAllByUserId(Long UserId, PageRequest pageRequest);

  //  Page<Request> findAllByRequestorIsNot(User user, Pageable pageable);

    @Query(" select r from Request  r " +
            "where r.requestor.id = ?1 " +
            "order by r.created desc ")
        //  List<Request> getAllByUserId(Long UserId, PageRequest pageRequest);
    List<Request> getAllByUserIdNoPage(Long UserId);
}
