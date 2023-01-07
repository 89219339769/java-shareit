package ru.practicum.shareit.request;

import ru.practicum.shareit.item.model.ItemDtoForRequest;

import java.util.ArrayList;
import java.util.List;

public class ItemRequestMapper {

    public static RequestDto toItemRequestDto(Request request) {
        List<ItemDtoForRequest> itemsForRequest = new ArrayList<>();
        return  RequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .items(itemsForRequest)
                .build();
    }

    public static RequestDtoForTest toItemRequestDtoForTest(RequestDto requestDto) {

        return  RequestDtoForTest.builder()
                .id(requestDto.getId())
                .description(requestDto.getDescription())
                .build();
    }
}
