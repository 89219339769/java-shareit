package ru.practicum.shareit.requestTests;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


import ru.practicum.shareit.ShareItApp;
import ru.practicum.shareit.item.ItemRepository;


import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;

import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;



@SpringBootTest(classes = ShareItApp.class)
class RequestRepositoryTests {
    @Autowired
    private RequestRepository itemRequestRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByRequestorIdOrderByCreatedAscTest() {
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.com");

        userRepository.save(user);
        itemRequestRepository.save(Request.builder().description("description").requestor(user)
                .created(LocalDateTime.now()).build());
        List<Request> items = itemRequestRepository.getAllByUserIdNoPage(user.getId());
        assertThat(items.size(), equalTo(1));
    }
}
