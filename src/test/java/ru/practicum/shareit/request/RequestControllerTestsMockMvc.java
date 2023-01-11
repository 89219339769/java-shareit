package ru.practicum.shareit.request;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = RequestController.class)

public class RequestControllerTestsMockMvc {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    RequestService requestService;

    @Autowired
    MockMvc mvc;
    @MockBean
    UserService userService;
    @MockBean
    UserRepository userRepository;
    Request request;
    List<RequestDto> listDtos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);

        request = Request.builder()
                .id(1L)
                .description("test")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        listDtos.add(ItemRequestMapper.toItemRequestDto(request));


    }

    @Test
    void createNewRequest() throws Exception {
        Mockito
                .when(requestService.addRequest(Mockito.anyLong(), Mockito.any()))
                .thenReturn(request);

        mvc.perform(post("/requests").header("X-Sharer-User-Id", 1L)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(request.getDescription()), String.class));
    }

    @Test
    void gettAll() throws Exception {
        Mockito
                .when(requestService.getAllRequestsWithItems(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(listDtos);

        mvc.perform(get("/requests/all").header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(request.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(request.getDescription())));
    }

    @Test
    void gettAllByUser() throws Exception {
        Mockito
                .when(requestService.getAllRequestsByUser(Mockito.anyLong()))
                .thenReturn(listDtos);

        mvc.perform(get("/requests").header("X-Sharer-User-Id", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(request.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(request.getDescription())));
    }

    @Test
    void getById() throws Exception {
        Mockito
                .when(requestService.getRequestById(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(ItemRequestMapper.toItemRequestDto(request));

        mvc.perform(get("/requests/1").header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(ItemRequestMapper.toItemRequestDto(request))));
    }
}




