package com.aninfo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public enum TransactionType {
    WITHDRAW,
    DEPOSIT;

    private static final Map<String, TransactionType> LOOKUP = stream(values())
                    .collect(toMap(TransactionType::toString, Function.identity()));

    @JsonCreator
    public static TransactionType from(final String value) {
        return LOOKUP.get(value.toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return this.toString().toLowerCase();
    }
}
