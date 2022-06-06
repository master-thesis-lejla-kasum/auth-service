package com.master.authservice.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum Municipality {
    JAJCE("Jajce"),
    SARAJEVO("Sarajevo"),
    BANJA_LUKA("Banja Luka"),
    MOSTAR("Mostar"),
    BRCKO("Brcko");

    @Getter
    private final String label;

    Municipality(String label) {
        this.label = label;
    }

    public static Optional<Municipality> valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(entity -> entity.getLabel().equals(label))
                .findFirst();
    }
}
