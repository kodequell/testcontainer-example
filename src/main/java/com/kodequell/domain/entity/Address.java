package com.kodequell.domain.entity;

import com.kodequell.domain.value.OrderId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Address {

    @PositiveOrZero
    private long id;

    @NotNull
    @Valid
    private OrderId orderId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String street;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String city;

    @NotBlank
    private String country;
}
