package ru.practicum.shareit.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.practicum.shareit.item.model.ItemDtoForRequest;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
public class RequestDtoForTest {
    private Long id;
    private String description;
}
