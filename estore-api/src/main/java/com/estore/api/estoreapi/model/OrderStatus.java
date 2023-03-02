package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("Under Review")
    UnderReview("Under Review"),
    @JsonProperty("Ordered")
    Ordered("Ordered"),
    @JsonProperty("Cancelled")
    Cancelled("Cancelled"),
    @JsonProperty("Fulfilled")
    Fulfilled("Fulfilled");

    private String status;

    private OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

}
