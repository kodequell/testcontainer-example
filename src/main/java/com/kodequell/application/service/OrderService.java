package com.kodequell.application.service;

import com.kodequell.application.command.CreateOrderCommand;
import com.kodequell.application.usecase.CreateOrderUseCase;
import com.kodequell.domain.entity.Address;
import com.kodequell.domain.entity.Order;
import com.kodequell.domain.repository.AddressRepository;
import com.kodequell.domain.repository.OrderRepository;
import com.kodequell.domain.value.CustomerId;
import com.kodequell.domain.value.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase {

    private final OrderRepository orderRepository;

    private final AddressRepository addressRepository;

    @Transactional
    @Override
    public OrderId createOrder(final CreateOrderCommand command) {

        final var customerId = new CustomerId(command.customerId());

        return addressRepository.getByCustomerId(customerId).map(orderMapper(customerId)).orElseThrow(() ->
                new IllegalStateException("Failed to resolve address for customerId: " + command.customerId()));
    }

    private Function<Address, OrderId> orderMapper(final CustomerId customerId) {
        return address -> {
            final var order = Order.builder().id(OrderId.generate()).customerId(customerId).address(address).build();
            orderRepository.insert(order);
            return order.getId();
        };
    }
}
