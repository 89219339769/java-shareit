package ru.practicum.shareit.item.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDtoForOwner  {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private long ownerId;
    private long requestId;
    private LocalDateTime startFuture;
    private LocalDateTime endFuture;
    private LocalDateTime startPast;
    private LocalDateTime endPast;

 //   private Collection<CommentDtoOut> comments;

//    @ToString
//    @Setter
//    @Getter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class BookingInfo {
//        private long id;
//        private long bookerId;
//        private LocalDateTime start;
//        private LocalDateTime end;
//    }

}