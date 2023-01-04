package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

//import ru.practicum.controllerTests.RequestControllerTestConfig;
//import ru.practicum.shareit.config.WebConfig;
import ru.practicum.shareit.controllerTests.HeaderKey;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestController;
import ru.practicum.shareit.request.RequestService;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




//
//@SpringJUnitWebConfig( { Request.class,  RequestService.class, RequestControllerTests.class})
//
//@WebMvcTest
//public class RequestControllerTests {
//    @Autowired
//    ObjectMapper mapper;
//    @MockBean
//    RequestService requestService;
//    @Autowired
//    MockMvc mvc;
//    Request request ;
//    @BeforeEach
//
//
//    void setUp() {
//        request =  Request.builder()
//                .id(1L)
//                .description("test")
//                .created(LocalDateTime.now())
//                .build();
//    }
//
//    @Test
//    void test1_createNewRequest() throws Exception {
//        Mockito
//                .when(requestService.addRequest(1, request))
//                .thenReturn(request);
//
//        mvc.perform(post("/requests")
//                        .content(mapper.writeValueAsString(request))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .header(HeaderKey.USER_KEY, 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(request.getId()), Long.class))
//                .andExpect(jsonPath("$.description", is(request.getDescription()), String.class));
//    }
//}


@WebMvcTest(controllers = RequestController.class)

public class RequestControllerTests {
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
    }

    @Test
    void test1_createNewRequest() throws Exception {
        Mockito
                .when(requestService.addRequest(Mockito.anyLong(), Mockito.any()))
          .thenReturn(request);

        mvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                         .header(HeaderKey.USER_KEY, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(request.getDescription()), String.class));
    }









}




