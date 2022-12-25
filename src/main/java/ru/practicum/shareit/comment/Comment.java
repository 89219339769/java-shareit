package ru.practicum.shareit.comment;


import lombok.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;


@Entity
@Table(name = "comments")
@ToString
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text_comment")
    private String text;

    @Column(name = "item_id", nullable = false)
    private long itemId;

    @Column(name = "author_ID", nullable = false)
    private long authorID;

//    @Column(name = "created_time", nullable = false)
//    private LocalDateTime createdTime;

}
