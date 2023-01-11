package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.ShareItApp;
import ru.practicum.shareit.item.ItemRepository;


import ru.practicum.shareit.item.model.Item;

import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;


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



    @Test
    void findAllusersRequestIdOrder() {
        User user = new User();
        user.setId(1L);
        user.setName("name1");
        user.setEmail("email@email1.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("name2");
        user2.setEmail("email@email2.com2");

        User user3 = new User();
        user3.setId(3L);
        user3.setName("name3");
        user3.setEmail("email@email3.com3");


        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        Request request = Request.builder().description("description").requestor(user)
                .created(LocalDateTime.now()).build();
        Request request2 = (Request.builder().description("description2").requestor(user2)
                .created(LocalDateTime.now()).build());
        Request request3 = Request.builder().description("description3").requestor(user3)
                .created(LocalDateTime.now()).build();

        itemRequestRepository.save(request);
        itemRequestRepository.save(request2);
        itemRequestRepository.save(request3);

        Item item = new Item(1L, "test", "test", true, 2L, user);
        Item item2 = new Item(2L, "test2", "test2", true, 3L, user);
        itemRepository.save(item);
        itemRepository.save(item2);

        List<Request> items = itemRequestRepository.getAllByUserIdNot(1L, PageRequest.of(0, 20));
        List<Request> itemsExp = List.of(request2);
        assertEquals(items.size(), itemsExp.size());
    }



}
