package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTests {
    @Autowired
    private UserController userController;

    private User user;

    @BeforeEach
    void init() {

        user = new User(1L, "user name", "user@email.com");
    }

    @Test
    void createTest() {
        User userDto = userController.create(user);
        assertEquals(userDto.getId(), userController.findUserById(userDto.getId()).getId());
    }

    @Test
    void updateTest() {
        userController.create(user);
        User user = new User(1L, "update name", "update@email.com");

        userController.update(1L, user);
        assertEquals(user.getEmail(), userController.findUserById(1L).getEmail());
    }

    @Test
    void updateByWrongUserTest() {
        assertThrows(NotFoundException.class, () -> userController.update(1L, user));
    }

    @Test
    void deleteTest() {
        User userDto = userController.create(user);
        assertEquals(1, userController.getAll().size());
        userController.deleteUser(userDto.getId());
        assertEquals(0, userController.getAll().size());
    }

    @Test
    void getByWrongIdTest() {
        assertThrows(NotFoundException.class, () -> userController.findUserById(99L));
    }
}




