package com.kodequell.testsupport;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodequell.domain.repository.OrderRepository;
import com.kodequell.infrastructure.spring.Application;
import com.kodequell.infrastructure.spring.config.MarshallingConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MarshallingConfig.class)
@Testcontainers
@ActiveProfiles("it")
@Sql("classpath:cleanup.sql")
@AutoConfigureMockMvc
@DirtiesContext
public abstract class IntegrationTestSupport implements TestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected OrderRepository orderRepository;

    protected static MockServerClient mockServerClient;

    @Container
    protected static MockServerContainer mockServerContainer =
            new MockServerContainer(DockerImageName.parse("mockserver/mockserver:latest"));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        mockServerClient = new MockServerClient(mockServerContainer.getHost(), mockServerContainer.getServerPort());
        registry.add("customer.api.url", mockServerContainer::getEndpoint);
    }

    @AfterEach
    protected void printExpectations() {
        Arrays.stream(mockServerClient.retrieveActiveExpectations(null)).forEach(System.err::println);
        Arrays.stream(mockServerClient.retrieveRecordedRequests(null)).forEach(System.err::println);
    }

    @SneakyThrows
    protected String toJson(Object any) {
        return objectMapper.writeValueAsString(any);
    }
}
