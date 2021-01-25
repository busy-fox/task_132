package com.elinvar.customer.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public interface BaseMessage {

    @JsonIgnore
    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    default String toJson() {
        return OBJECT_MAPPER.writeValueAsString(this);
    }
}
