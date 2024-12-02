package com.kodequell.infrastructure.rest.repository;

import java.util.Optional;

import com.kodequell.domain.entity.Address;
import com.kodequell.domain.repository.AddressRepository;
import com.kodequell.domain.value.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
@RequiredArgsConstructor
public class HttpAddressRepository implements AddressRepository {

    private final RestClient restClient;

    @Override
    public Optional<Address> getByCustomerId(final CustomerId customerId) {

        return Optional.ofNullable(restClient.get()
                 .uri("/{customerId}", customerId.value())
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {})
                 .body(Address.class));
    }
}
