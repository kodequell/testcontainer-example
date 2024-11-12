package com.kodequell.infrastructure.rest.controller;

import com.kodequell.application.command.CreateOrderCommand;
import com.kodequell.application.dto.OrderCreatedDto;
import com.kodequell.application.usecase.CreateOrderUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public @Valid OrderCreatedDto createOrder(@NotNull @Valid @RequestBody final CreateOrderCommand command) {
        return new OrderCreatedDto(createOrderUseCase.createOrder(command).value());
    }
}
