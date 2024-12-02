package com.kodequell.infrastructure.rest.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.kodequell.domain.repository.AddressRepository;
import com.kodequell.testsupport.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

class HttpAddressRepositoryIT extends IntegrationTestSupport {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void testGetByCustomerId() {

        // prepare
        mockServerClient.when(request().withMethod("GET").withPath("/" + CUSTOMER_ID), exactly(1))
                        .respond(response().withStatusCode(200).withContentType(MediaType.APPLICATION_JSON).withBody(toJson(defaultAddress())));

        // action
        var result = addressRepository.getByCustomerId(defaultCustomerId());

        // verify
        assertThat(result).hasValueSatisfying(address -> assertThat(address).usingRecursiveComparison().isEqualTo(defaultAddress()));
    }

    @Test
    void testGetByCustomerIdWithAddressNotFound() {

        // prepare
        mockServerClient.when(request().withMethod("GET").withPath("/" + CUSTOMER_ID), exactly(1))
                        .respond(response().withStatusCode(404));

        // action
        var result = addressRepository.getByCustomerId(defaultCustomerId());

        // verify
        assertThat(result).isEmpty();
    }
}
