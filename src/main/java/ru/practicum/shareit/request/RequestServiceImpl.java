package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import java.awt.print.Pageable;
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
    public List<RequestDto> getAllRequestsWithItems(long userId, int from, int size) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));

        List<Request> requests = requestRepository.getAllByUserId(userId, PageRequest.of
                (from, size, Sort.by(Sort.Direction.DESC, "created")));
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

    @Override
    public List<RequestDto> getAllRequests(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));

        List<Request> requests = requestRepository.findAll();

        List<RequestDto> requestsDtos = new ArrayList<>();

        for (Request request : requests) {
            requestsDtos.add(ItemRequestMapper.toItemRequestDto(request));
        }
        return requestsDtos;
    }

    @Override
    public RequestDto getRequestById(Long userId, Long requestId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден запрос с id: " + requestId));

       return  ItemRequestMapper.toItemRequestDto(request);

    }


    //найти итемы по номеру запроса

}
