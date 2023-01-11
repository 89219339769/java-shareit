package ru.practicum.shareit.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import ru.practicum.shareit.item.model.Item;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class JsonItemTest {
    @Autowired
    JacksonTester<Item> json;

    @Test
    void testItemDto() throws Exception {
        Item item = Item
                .builder()
                .id(1L)
                .name("item")
                .available(true)
                .description("descriptionOfItem")
                .build();

        JsonContent<Item> result = json.write(item);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("descriptionOfItem");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
    }
}
