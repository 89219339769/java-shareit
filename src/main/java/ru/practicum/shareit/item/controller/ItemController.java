package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item add(@RequestHeader("X-Sharer-User-Id") Long userId,
                    @RequestBody Item item) {
        return itemService.save(userId, item);
    }

//    @GetMapping("/{id}")
//    public Item findUserById(@PathVariable Long id) {
//        return itemService.findItemById(id);
//    }

    @PatchMapping("/{itemId}")
    public Item put(@PathVariable long itemId,
            @RequestHeader("X-Sharer-User-Id") Long userId,
                    @RequestBody Item item) {
        return itemService.updateItem(itemId, userId, item);
    }



}
