package ru.practicum.shareit.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> findAllByItemIdIs(long itemId);
}
