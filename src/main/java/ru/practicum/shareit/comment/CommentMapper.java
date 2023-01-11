package ru.practicum.shareit.comment;

import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public static CommentDtoOut toCommentDt0FromComment(Comment comment) {
        return CommentDtoOut.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }
}
