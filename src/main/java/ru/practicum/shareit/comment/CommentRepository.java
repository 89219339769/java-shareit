package ru.practicum.shareit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> findAllByItemIdIs(long itemId);


    @Query(value = "select *" +
            "    from Comments as c\n" +
            "    inner join Items as i on i.id = c.item_id\n" +
            "    inner join Users as u on u.id = i.id_owner\n" +
            "    where u.id = ?1",
            nativeQuery = true)
    List<Comment> getAllCommentsByUserId(Long userId);
}
