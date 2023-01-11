package ru.practicum.shareit.user;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
    class UserControllerTestsMockMvc {
        @Autowired
        private ObjectMapper mapper;

        @MockBean
        private UserService userService;

        @Autowired
        private MockMvc mvc;

        private User user;

        @BeforeEach
        void init() {
            user = new User(1L,"user name","user@email.com");

        }

        @Test
        void getAllTest() throws Exception {
            when(userService.getAllUsers())
                    .thenReturn(List.of(user));
            mvc.perform(get("/users")
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("X-Sharer-User-Id", 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(List.of(user))));
        }

        @Test
        void getByIdTest() throws Exception {
            when(userService.get(anyLong()))
                    .thenReturn(user);
            mvc.perform(get("/users/1")
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("X-Sharer-User-Id", 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(user)));
        }

        @Test
        void createTest() throws Exception {
            when(userService.saveUser(any()))
                    .thenReturn(user);
            mvc.perform(post("/users")
                            .content(mapper.writeValueAsString(user))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("X-Sharer-User-Id", 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(user)));
        }

        @Test
        void updateTest() throws Exception {
            when(userService.updateUser(anyLong(),any()))
                    .thenReturn(user);
            mvc.perform(patch("/users/1")
                            .content(mapper.writeValueAsString(user))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("X-Sharer-User-Id", 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(user)));
        }
    }










