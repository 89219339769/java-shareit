package ru.practicum.shareit.user;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Data
@RequiredArgsConstructor
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private  Integer userId = 1;
    public List<User> getAll() {
        return users;
    }

    public User create(User user) {
        user.setId(Long.valueOf(userId++));
        users.add(user);
        return user;
    }

    public User get(Long id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                User user = users.get(i);
                return user;
            }
        }
        throw new NotFoundException("юзера с этим номером не найдено");
    }

    public void delete(Long id) {
        boolean userExist = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                userExist = true;
                users.remove(i);
                log.info("пользователь {id} удален");
            }
        }
        if (userExist == false) {
            throw new NotFoundException("юзера с этим номером не найдено");
        }
    }
}