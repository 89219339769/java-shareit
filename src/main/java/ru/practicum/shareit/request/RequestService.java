package ru.practicum.shareit.request;

import java.util.List;

public interface RequestService {
    Request addRequest(long userId, Request request);

    List<RequestDto> getAllRequests(long userId);
}
