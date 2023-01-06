package ru.practicum.shareit.request;

import java.util.List;

public interface RequestService {
    Request addRequest(long userId, Request request);

    List<RequestDto> getAllRequestsWithItems(long userId, int from, int size);

    List<RequestDto> getAllRequestsByUser(Long userId);

    RequestDto getRequestById(Long userId, Long requestId);
}
