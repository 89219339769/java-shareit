package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;


import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class RequestControllerJsonTest {
    @Autowired
    JacksonTester<RequestDto> json;

    @Test
    void testItemRequestDto() throws Exception {
        RequestDto itemRequestDto = RequestDto
                .builder()
                .id(1L)
                .description("description")
                .build();

        JsonContent<RequestDto> result = json.write(itemRequestDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description")
                .isEqualTo("description");
    }
}