package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.ItemDtoShort;


@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;


    @PostMapping
    public Request add(@RequestHeader("X-Sharer-User-Id") Long userId,
                       @RequestBody Request request) {


        return requestService.add(userId, request);
    }


}
