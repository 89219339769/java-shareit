package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoAbstract;
import ru.practicum.shareit.item.model.ItemDtoForOwner;
import ru.practicum.shareit.item.model.ItemDtoShort;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemServiceImpl itemService;

    @PostMapping
    public ItemDtoShort add(@RequestHeader("X-Sharer-User-Id") Long userId,
                    @RequestBody Item item) {
     return     itemService.save(userId, item);

    }


    @GetMapping("/{id}")
    public ItemDtoAbstract getById(@PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.findItemById(id, userId);
    }

    @PatchMapping("/{itemId}")
    public Item put(@PathVariable long itemId,
                    @RequestHeader("X-Sharer-User-Id") Long userId,
                    @RequestBody Item item) {
        return itemService.updateItem(itemId, userId, item);

    }

    @GetMapping
    public List<ItemDtoShort> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
      return itemService.findItemsByUserId(userId);
    }


    @GetMapping("search")
    public List<ItemDtoShort> findFilmsBySearch(@RequestParam(name = "text") String query) {

        return itemService.findItemByNameOrDescription(query);

    }


}
