package com.master.authservice.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum Canton {
    KS("Kanton Sarajevo"),
    SBK("Srednjobosanski kanton"),
    ZDK("Zenicko-dobojski kanton"),
    HNK("Hercegovacko-neretvanski kanton");

    @Getter
    private final String label;

    Canton(String label) {
        this.label = label;
    }

    public static Optional<Canton> valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(entity -> entity.getLabel().equals(label))
                .findFirst();
    }
}
