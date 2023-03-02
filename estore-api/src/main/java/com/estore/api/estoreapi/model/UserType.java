package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserType {
    @JsonProperty("Customer")
    Customer("Customer"),
    @JsonProperty("Admin")
    Admin("Admin"),
    @JsonProperty("Ornithologist")
    Ornithologist("Ornithologist");

    private String type;

    private UserType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
