package com.kodequell.application.command;

import com.kodequell.domain.value.CustomerId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateOrderCommand(@NotNull @Pattern(regexp = CustomerId.PATTERN) String customerId) {

}
