package com.kodequell.infrastructure.rest.controller;

import com.kodequell.application.command.CreateOrderCommand;
import com.kodequell.domain.value.OrderId;
import com.kodequell.testsupport.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.mockserver.model.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerIT extends IntegrationTestSupport {

    @Test
    void testCreateOrderWithSuccess() throws Exception {

        // prepare
        assertThat(orderRepository.findAll()).isEmpty();

        mockServerClient.when(request().withMethod("GET").withPath("/" + CUSTOMER_ID), exactly(1))
                .respond(response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(toJson(defaultAddress())));

        // action
        mockMvc.perform(post("/order").contentType(APPLICATION_JSON).content(toJson(defaultCreateOrderCommand())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", matchesPattern(OrderId.PATTERN)));

        // verify database
        assertThat(orderRepository.findAll()).singleElement()
                .usingRecursiveComparison().ignoringFields("id.value", "address.orderId.value")
                .isEqualTo(defaultOrder());
    }

    @Test
    void testCreateOrderWithInvalidCustomerId() throws Exception {

        // prepare
        assertThat(orderRepository.findAll()).isEmpty();

        // action
        mockMvc.perform(post("/order").contentType(APPLICATION_JSON).content(toJson(new CreateOrderCommand("foo"))))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // verify database
        assertThat(orderRepository.findAll()).isEmpty();
    }
}
