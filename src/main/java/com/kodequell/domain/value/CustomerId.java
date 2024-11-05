package com.kodequell.domain.value;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CustomerId(@NotNull @Pattern(regexp = PATTERN) String value) {

    public static final String PATTERN = "C_[A-Z0-9]{8}";
}
