package ru.practicum.shareit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> findAllByItemIdIs(long itemId);







        @Query("select c.id,\n" +
                "    c.author.id,\n" +
                "    c.created,\n" +
                "    c.item.id,\n" +
                "    c.text\n" +
                "    from Comment as c\n" +
                "    inner join Item as i on i.id = c.item.id\n" +
                "    inner join User as u on u.id = i.owner.id\n" +
                "    where u.id = ?1")
    List<Comment> getAllCommentsByUserId(Long UserId);





}
