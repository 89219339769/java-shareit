package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

@Slf4j
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
        repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));
        List<Request> requests = requestRepository.getAllByUserIdNot(userId, PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "created")));
        List<RequestDto> requestsDtos = new ArrayList<>();

        Collection<Item> itemsList = itemRepository.findAll();
        for (Request request : requests) {
            List<ItemDtoForRequest> itemsListAnswers = itemsList.stream()
                    .map(ItemMapper::itemToItemForRequest)
                    .collect(Collectors.toList());
            RequestDto requestDto = ItemRequestMapper.toItemRequestDto(request);

            List<ItemDtoForRequest> listWithAnswers = new ArrayList<>();
            for (ItemDtoForRequest itemDtoForRequest : itemsListAnswers)
                if (itemDtoForRequest.getRequestId() != 0)
                    listWithAnswers.add(itemDtoForRequest);
            requestDto.setItems(listWithAnswers);
            requestsDtos.add(requestDto);
        }
        return requestsDtos;
    }


    @Override
    public List<RequestDto> getAllRequestsByUser(Long userId) {
        repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));

        List<Request> requests = requestRepository.getAllByUserIdNoPage(userId);
        List<RequestDto> requestsDtos = new ArrayList<>();

        Collection<Item> itemsList = itemRepository.findAllwithRequestId();

        for (Request request : requests) {
            List<ItemDtoForRequest> itemsListAnswers;
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
    public RequestDto getRequestById(Long userId, Long requestId) {
        repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден пользователь с id: " + userId));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Невозможно создать запрос - " +
                        "не найден запрос с id: " + requestId));

        Collection<Item> itemsList = itemRepository.findAllwithRequestId();

        List<ItemDtoForRequest> itemsListAnswers = itemsList.stream()
                .filter(item -> item.getRequestId().equals(request.getId()))
                .map(ItemMapper::itemToItemForRequest)
                .collect(Collectors.toList());
        RequestDto requestDto = ItemRequestMapper.toItemRequestDto(request);
        requestDto.setItems(itemsListAnswers);
        return requestDto;
    }
}
