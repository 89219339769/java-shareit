package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository repository;


    @Override
    public User saveUser(User user) {
        repository.save(user);
        return user;
    }


    public List<User> getAllUsers() {
        return repository.findAll();

    }

       public Optional<User> get(Long id) {
        return repository.findById(id);
   }


   public void delete(Long id) {
        repository.deleteById(id);

    }




}
//
//    public User saveUser(User user) {
////        List<User> users = repository.getUsers();
////        String userEmail = user.getEmail();
////        validator.validateDublicateEmail(users, userEmail);
////       //по аналогии остальные методы сделать (обращение к базе тут обработка там)
////       //Сделать 2 класса validator (один для user один для item)
////
////        validator.validateNoEmail(user);
////        validator.validateIncorrectEmail(user);
////
////        return repository.create(user);
//        return  null;
//    }
//
//    public User updateUser(Long id, User user) {
////        user.setId(id);
////        for (int j = 0; j < repository.getUsers().size(); j++) {
////            if (repository.getUsers().get(j).getEmail().equals(user.getEmail()))
////                throw new EmailWrongException("адрес указанной обновляемой электронной почты уже сущетсвует ");
////        }
////
////        if (repository.getUsers().get((int) (id-1)) == null) {
////            throw new NotFoundException("невозможно обновить, т.к. пользователя с этим номером не существует ");}
////
////        User updateUser = repository.getUsers().get(Math.toIntExact(id) - 1);
////        if (user.getEmail() != null && user.getEmail() != updateUser.getEmail()) {
////            validator.validateNoEmail(user);
////            validator.validateDublicateEmail( repository.getUsers(),user.getEmail());
////            validator.validateIncorrectEmail(user);
////
////            updateUser.setEmail(user.getEmail());
////        }
////        if (user.getName() != null && user.getName() != updateUser.getName()) {
////            updateUser.setName(user.getName());
////        }
////        return updateUser;
//        return null;
//    }
//

//
//    public void delete(Long id) {
//
////        repository.delete(id);
//
//    }
