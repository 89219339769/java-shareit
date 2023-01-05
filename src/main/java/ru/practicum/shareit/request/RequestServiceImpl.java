package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoForRequest;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final UserRepository repository;

    private final RequestRepository requestRepository;

    private final ItemRepository itemRepository;

    @Override
    public Request addRequest(long userId, Request request) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));

        if (request.getDescription() == null) {
            throw new BadRequestException("нужно указать описание");
        }

        request.setRequestor(user);
        request.setCreated(LocalDateTime.now());
        requestRepository.save(request);
        return request;
    }


    @Override
    public List<RequestDto> getAllRequests(long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));

        List<Request> requests = requestRepository.getAllByUserId(userId);
        List<RequestDto> requestsDtos = new ArrayList<>();


        //большая ошибкаааааааа
        Collection<Item> itemsList = itemRepository.findAll();

        for (Request request : requests) {

            List<ItemDtoForRequest> itemsListAnswers = new ArrayList<>();

            itemsListAnswers = itemsList.stream()
                    .filter(item -> item.getRequestId().equals(request.getId()))
                    .map(ItemMapper::itemToItemForRequest)
                    .collect(Collectors.toList());

            RequestDto requestDto = ItemRequestMapper.toItemRequestDto(request);
            requestDto.setItems(itemsListAnswers);
            requestsDtos.add(requestDto);

        }
        return requestsDtos;

    }


    //найти итемы по номеру запроса

}
