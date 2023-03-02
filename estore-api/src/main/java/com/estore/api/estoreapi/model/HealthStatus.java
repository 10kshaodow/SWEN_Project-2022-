package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HealthStatus {
    @JsonProperty("Superb")
    Superb("Superb"),
    @JsonProperty("Well")
    Well("Well"),
    @JsonProperty("Unwell")
    Unwell("Unwell"),
    @JsonProperty("Dead")
    Dead("Dead");

    private String status;

    private HealthStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
