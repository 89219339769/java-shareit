package ru.practicum.shareit.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@Builder
@ToString
public class RequestDtoForTest {
    private Long id;
    private String description;
}
