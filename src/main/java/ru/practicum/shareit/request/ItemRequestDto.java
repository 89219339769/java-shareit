package ru.practicum.shareit.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class ItemRequestDto {
    private Long id;
    private String description;
    private LocalDateTime created;

}
