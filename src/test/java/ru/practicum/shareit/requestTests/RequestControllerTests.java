package ru.practicum.shareit.requestTests;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestController;

import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RequestControllerTests {
    @Autowired
    private RequestController itemRequestController;

    @Autowired
    private UserController userController;

    private Request request;

    private User user;

    @BeforeEach
    void init() {
        request = Request.builder()
                .description("test")
                .build();

        user = new User();
        user.setName("name");
        user.setEmail("user@email.com");

    }

    @Test
    void createRequestTest() {

        userController.create(user);
        Request itemRequest = itemRequestController.add(user.getId(), request);
        assertEquals(1L, itemRequestController.getRequestById(itemRequest.getId(), user.getId()).getId());
    }
}
