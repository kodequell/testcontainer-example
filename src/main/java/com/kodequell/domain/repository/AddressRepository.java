package com.kodequell.domain.repository;

import com.kodequell.domain.entity.Address;
import com.kodequell.domain.value.CustomerId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface AddressRepository {

    Optional<@Valid Address> getByCustomerId(@NotNull @Valid CustomerId customerId);
}
