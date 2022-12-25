package ru.practicum.shareit.comment;


import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

@Component
public class CommentMapper {

    public static CommentDtoOut toCommentDt0FromComment(Comment comment, User author) {
        return new CommentDtoOut(
                comment.getId(),
                comment.getText(),
                author.getName()
        );
    }
}
