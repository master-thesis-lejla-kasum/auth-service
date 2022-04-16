package com.master.authservice.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum Entity {
    FBIH("Federacija Bosne i Hercegovine"),
    RS("Republika Srpska"),
    BD("Brcko Distrikt");

    @Getter
    private final String label;

    Entity(String label) {
        this.label = label;
    }

    public static Optional<Entity> valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(entity -> entity.getLabel().equals(label))
                .findFirst();
    }

}
