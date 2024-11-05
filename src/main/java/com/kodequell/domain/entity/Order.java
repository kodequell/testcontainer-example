package com.kodequell.domain.entity;

import com.kodequell.domain.value.CustomerId;
import com.kodequell.domain.value.OrderId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Order {

    @NotNull
    @Valid
    @EqualsAndHashCode.Include
    private OrderId id;

    @NotNull
    @Valid
    private CustomerId customerId;

    @NotNull
    @Valid
    private Address address;
}
