package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final UserRepository repository;

    private final RequestRepository requestRepository;


    @Override
    public Request addRequest(long userId, Request request) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));

        if(request.getDescription()==null){
            throw new BadRequestException("нужно указать описание");
        }

        request.setRequestor(user);
        request.setCreated(LocalDateTime.now());
        requestRepository.save(request);
        return request;
    }


}
