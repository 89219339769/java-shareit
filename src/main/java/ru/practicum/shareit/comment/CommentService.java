package ru.practicum.shareit.comment;

import java.util.Optional;

public interface CommentService {


    CommentDtoOut addComment(Long userId, Long itemId, Comment comment);

    Optional<Comment> getCommentsByIetmId(Long itemId);
}
