package com.kodequell.domain.value;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.RandomStringUtils;

public record OrderId(@NotNull @Pattern(regexp = PATTERN) String value) {

    public static final String PATTERN = "O_[A-Z0-9]{8}";

    public static OrderId generate() {

        return new OrderId("O_" + RandomStringUtils.secure().nextAlphanumeric(8).toUpperCase());
    }
}
