package ru.practicum.shareit.request;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(" select r from Request  r " +
            "join Item i on i.requestId = r.id " +
            "where r.requestor.id <> ?1  and i.requestId <> 0" +
            "order by r.created desc ")
    List<Request> getAllByUserIdNot(Long userId, PageRequest pageRequest);

    @Query(" select r from Request  r " +
            "where r.requestor.id = ?1 " +
            "order by r.created desc ")
    List<Request> getAllByUserId(Long userId, PageRequest pageRequest);


    @Query(" select r from Request  r " +
            "where r.requestor.id = ?1 " +
            "order by r.created desc ")
    List<Request> getAllByUserIdNoPage(Long userId);
}
