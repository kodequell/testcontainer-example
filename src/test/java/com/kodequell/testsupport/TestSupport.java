package com.kodequell.testsupport;

import com.kodequell.application.command.CreateOrderCommand;
import com.kodequell.domain.entity.Address;
import com.kodequell.domain.entity.Order;
import com.kodequell.domain.value.CustomerId;
import com.kodequell.domain.value.OrderId;

public interface TestSupport {

    String CUSTOMER_ID = "C_4ERL65FG";
    String ORDER_ID = "O_XR76TZ1T";

    default Order defaultOrder() {
        return Order.builder().id(defaultOrderId()).address(defaultAddress()).customerId(defaultCustomerId()).build();
    }

    default Address defaultAddress() {
        return Address.builder()
                .id(1L)
                .orderId(defaultOrderId())
                .firstName("Max")
                .lastName("Mustermann")
                .street("Musterstr. 1")
                .zipCode("12345")
                .city("Musterstadt")
                .country("Musterland")
                .build();
    }

    default OrderId defaultOrderId() {
        return new OrderId(ORDER_ID);
    }

    default CustomerId defaultCustomerId() {
        return new CustomerId(CUSTOMER_ID);
    }

    default CreateOrderCommand defaultCreateOrderCommand() {
        return new CreateOrderCommand(CUSTOMER_ID);
    }
}
