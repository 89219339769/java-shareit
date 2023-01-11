package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.comment.CommentDtoOut;
import ru.practicum.shareit.comment.CommentService;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoForOwner;
import ru.practicum.shareit.item.model.ItemDtoShort;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTstsMockMvc {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemService itemService;
    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mvc;
    private ItemDtoShort itemDtoShort;

    private ItemDtoForOwner itemDtoForOwner;


    private CommentDtoOut commentDtoOut;
    private Item item;

    @BeforeEach
    void init() {

        commentDtoOut = CommentDtoOut.builder()
                .id(1L)
                .text("test")
                .authorName("name")
                .created(LocalDateTime.now())
                .build();

        itemDtoShort = ItemDtoShort
                .builder()
                .id(1L)
                .name("item name")
                .description("item description")
                .available(true)
                .build();


        item = Item
                .builder()
                .id(1L)
                .name("item name")
                .description("item description")
                .available(true)
                .build();

        itemDtoForOwner = ItemDtoForOwner
                .builder()
                .id(1L)
                .name("item name")
                .description("item description")
                .available(true)
                .build();


    }

    @Test
    void addItemTest() throws Exception {
        Mockito
                .when(itemService.save(Mockito.anyLong(), Mockito.any()))
                .thenReturn(itemDtoShort);


        mvc.perform(post("/items").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(itemDtoShort))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(itemDtoShort)));

    }

    @Test
    void updateItemByIdTest() throws Exception {
        Mockito
                .when(itemService.updateItem(Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
                .thenReturn(item);


        mvc.perform(patch("/items/1").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(itemDtoShort))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(item)));

    }

    @Test
    void getItemByIdTest() throws Exception {
        Mockito
                .when(itemService.findItemById(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(itemDtoShort);


        mvc.perform(get("/items/1").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(itemDtoShort))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(itemDtoShort)));

    }


    @Test
    void getAllItemByIdTest() throws Exception {
        Mockito
                .when(itemService.getAllItems(Mockito.anyLong()))
                .thenReturn(List.of(itemDtoForOwner));


        mvc.perform(get("/items").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(List.of(itemDtoForOwner)))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(itemDtoForOwner))));

    }


    @Test
    void findItemsBySearch() throws Exception {
        Mockito
                .when(itemService.findItemByNameOrDescription(Mockito.anyString()))
                .thenReturn(List.of(itemDtoShort));

        mvc.perform(get("/items/search?text='name'").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(List.of(itemDtoShort)))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(itemDtoShort))));

    }

    @Test
    void addCommentToItems() throws Exception {
        Mockito
                .when(commentService.addComment(Mockito.anyLong(), Mockito.anyLong(), any()))
                .thenReturn(commentDtoOut);


        mvc.perform(post("/items/1/comment").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(commentDtoOut))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentDtoOut)));

    }
}