package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.CommentService;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.ItemDtoAbstract;
import ru.practicum.shareit.item.model.ItemDtoForOwner;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDtoOut;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoShort;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final CommentService commentService;
    private final ItemService itemService;

    @PostMapping
    public ItemDtoShort add(@RequestHeader("X-Sharer-User-Id") Long userId,
                            @RequestBody Item item) {
        return itemService.save(userId, item);
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
    public Collection<ItemDtoForOwner> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllItems(userId);
    }

    @GetMapping("search")
    public List<ItemDtoShort> findItemsBySearch(@RequestParam(name = "text") String query) {

        return itemService.findItemByNameOrDescription(query);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoOut add(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @PathVariable Long itemId,
                             @RequestBody Comment comment) {
        return commentService.addComment(userId, itemId, comment);
    }
}
