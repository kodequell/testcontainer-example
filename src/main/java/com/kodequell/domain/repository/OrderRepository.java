package com.kodequell.domain.repository;

import com.kodequell.domain.entity.Order;
import com.kodequell.domain.value.OrderId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
public interface OrderRepository {

    void insert(@NotNull @Valid Order order);

    List<@NotNull @Valid Order> findAll();

    Optional<@Valid Order> getById(@NotNull @Valid OrderId orderId);
}
