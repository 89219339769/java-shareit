package ru.practicum.shareit.request;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.model.Item;

import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RequestControllerTests {
    @Autowired
    private RequestController itemRequestController;

    @Autowired
    private ItemController itemController;
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

    @Test
    void getByIdRequestTest() {
        userController.create(user);
        itemRequestController.add(user.getId(), request);
        RequestDto requestDto = ItemRequestMapper.toItemRequestDto(request);
        RequestDtoForTest requestDtoForTest = ItemRequestMapper.toItemRequestDtoForTest(requestDto);

        RequestDto requestDtoTest = itemRequestController.getRequestById(1L, 1L);
        RequestDtoForTest requestDtoForTestTest = ItemRequestMapper.toItemRequestDtoForTest(requestDtoTest);
        assertEquals(requestDtoForTest, requestDtoForTestTest);

    }

    @Test
    void getByWrongIdRequestTest() {
        userController.create(user);
        itemRequestController.add(user.getId(), request);
        assertThrows(NotFoundException.class, () -> itemRequestController.getRequestById(2L, user.getId()));

    }


    @Test
    void getAllRequestByOwnerTest() {
        userController.create(user);
        itemRequestController.add(user.getId(), request);

        List<RequestDto> requestDtoTests = itemRequestController.getAll(user.getId());
        List<RequestDtoForTest> requestDtoTestsFortest = new ArrayList<>();
        for (RequestDto requestDto : requestDtoTests) {
            requestDtoTestsFortest.add(ItemRequestMapper.toItemRequestDtoForTest(requestDto));
        }

        RequestDto requestDto = ItemRequestMapper.toItemRequestDto(request);
        RequestDtoForTest requestDtoForTest = ItemRequestMapper.toItemRequestDtoForTest(requestDto);
        List<RequestDtoForTest> requestDtoTestsTest = List.of(requestDtoForTest);


        assertEquals(requestDtoTestsTest, requestDtoTestsFortest);
    }

    @Test
    void getAllRequestByOtherTest() {
        User user2 = new User();
        user2.setName("name2");
        user2.setEmail("user@email2.com");
        Item item = new Item(1L, "test", "test", true, 1L, user2);

        userController.create(user);
        userController.create(user2);
        itemRequestController.add(2L, request);
        itemController.add(1L, item);

        List<RequestDto> requestDtoTests = itemRequestController.getAllWithItems(1L, 0, 10);
        List<RequestDtoForTest> requestDtoTestsForTest = new ArrayList<>();
        for (RequestDto requestDto : requestDtoTests) {
            requestDtoTestsForTest.add(ItemRequestMapper.toItemRequestDtoForTest(requestDto));
        }

        RequestDto requestDto = ItemRequestMapper.toItemRequestDto(request);
        RequestDtoForTest requestDtoForTest = ItemRequestMapper.toItemRequestDtoForTest(requestDto);
        List<RequestDtoForTest> requestDtoTeststest = List.of(requestDtoForTest);

        assertEquals(requestDtoTeststest.get(0).getId(), requestDtoTestsForTest.get(0).getId());
    }
}
