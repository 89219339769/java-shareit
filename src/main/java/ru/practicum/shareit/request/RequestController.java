package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;


    @PostMapping
    public Request add(@RequestHeader("X-Sharer-User-Id") Long userId,
                       @RequestBody Request request) {


        return requestService.addRequest(userId, request);
    }


}
