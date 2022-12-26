package ru.practicum.shareit.comment;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
@Builder
public class CommentDtoOut {
    private long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
