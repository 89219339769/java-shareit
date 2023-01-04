package ru.practicum.shareit.request;

public class ItemRequestMapper {



    public static ItemRequestDto toItemRequestDto(Request itemRequest) {
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .build();
    }





}
