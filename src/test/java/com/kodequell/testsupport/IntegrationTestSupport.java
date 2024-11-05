package com.kodequell.testsupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodequell.domain.repository.OrderRepository;
import com.kodequell.infrastructure.spring.Application;
import com.kodequell.infrastructure.spring.config.MarshallingConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;

;

@SpringBootTest(classes = Application.class)
@Import(MarshallingConfig.class)
@Testcontainers
@ActiveProfiles("it")
@Sql("classpath:cleanup.sql")
@AutoConfigureMockMvc
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

    @BeforeAll
    static void createMockServerClient() {
        mockServerClient = new MockServerClient(mockServerContainer.getHost(), mockServerContainer.getServerPort());
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("customer.api.url", mockServerContainer::getEndpoint);
    }

    @AfterEach
    protected void resetMockServer() {
        Arrays.stream(mockServerClient.retrieveActiveExpectations(null)).forEach(System.err::println);
        Arrays.stream(mockServerClient.retrieveRecordedRequests(null)).forEach(System.err::println);
        mockServerClient.reset();
    }

    @SneakyThrows
    protected String toJson(Object any) {
        return objectMapper.writeValueAsString(any);
    }
}
