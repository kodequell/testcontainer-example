package com.kodequell.application.usecase;

import com.kodequell.application.command.CreateOrderCommand;
import com.kodequell.domain.value.OrderId;

public interface CreateOrderUseCase {

    OrderId createOrder(CreateOrderCommand createOrderCommand);
}
