package com.kodequell.application.dto;

import com.kodequell.domain.value.OrderId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OrderCreatedDto(@NotNull @Pattern(regexp = OrderId.PATTERN) String orderId) {

}
