package com.leovegas.wallet.dto.rest_response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RestResponseType {
    SUCCESS("success"),
    ERROR("error"),
    WARNING("warning"),
    INFO("info");

    private String name;

    RestResponseType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
