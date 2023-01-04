//package ru.practicum.shareit.controllerTests;
//
//public class UserControllerTest {
//}
//package ru.practicum.shareit.mvc.user;
//
//        import com.fasterxml.jackson.databind.ObjectMapper;
//        import org.junit.jupiter.api.BeforeEach;
//        import org.junit.jupiter.api.Test;
//        import org.mockito.InjectMocks;
//        import org.mockito.Mock;
//        import org.springframework.http.MediaType;
//        import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
//        import org.springframework.test.web.servlet.MockMvc;
//        import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//        import ru.practicum.shareit.user.controller.UserController;
//        import ru.practicum.shareit.user.dto.UserDto;
//        import ru.practicum.shareit.user.service.IUserService;
//
//        import java.nio.charset.StandardCharsets;
//        import static org.hamcrest.Matchers.is;
//        import static org.mockito.ArgumentMatchers.any;
//        import static org.mockito.Mockito.when;
//        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringJUnitWebConfig({ UserController.class, ru.practicum.user.UserControllerTestConfig.class,
//        ru.practicum.config.WebConfig.class})
//public class UserControllerTest {
//    @Mock
//    private IUserService userService;
//
//    @InjectMocks
//    private UserController controller;
//
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    private MockMvc mvc;
//
//    private UserDto userDto;
//
//    @BeforeEach
//    void setUp() {
//        mvc = MockMvcBuilders
//                .standaloneSetup(controller)
//                .build();
//
//        userDto = new UserDto(1L, "User1", "user1@email.ru");
//    }
//
//    @Test
//    void createUserTest() throws Exception {
//        when(userService.createUser(any()))
//                .thenReturn(userDto);
//
//        mvc.perform(post("/users")
//                        .content(mapper.writeValueAsString(userDto))
//                        .characterEncoding(StandardCharsets.UTF_8)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
//                .andExpect(jsonPath("$.name", is(userDto.getName())))
//                .andExpect(jsonPath("$.email", is(userDto.getEmail())));
//    }
//
//    @Test
//    void getUserByIdTest() {
//
//    }
//
//}
//
//package ru.practicum.user;
//
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.context.annotation.Configuration;
//        import ru.practicum.shareit.user.service.IUserService;
//
//        import static org.mockito.Mockito.mock;
//
//@Configuration
//public class UserControllerTestConfig {
//    @Bean
//    public IUserService userService() {
//        return mock(IUserService.class);
//    }
//}
//
//package ru.practicum.config;
//
//        import org.springframework.context.annotation.Configuration;
//        import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@Configuration // помечает класс как java-config для контекста приложения
//@EnableWebMvc // призывает импортировать дополнительную конфигурацию для веб-приложений
//public class WebConfig {
//}