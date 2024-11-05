package com.kodequell.infrastructure.mariadb.repository;

import com.kodequell.domain.repository.OrderRepository;
import com.kodequell.domain.value.OrderId;
import com.kodequell.testsupport.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcOrderRepositoryIT extends IntegrationTestSupport {

    @Autowired
    private OrderRepository testSubject;

    @Test
    void testInsert() {

        // prepare
        assertThat(testSubject.findAll()).isEmpty();

        // action
        testSubject.insert(defaultOrder());

        // verify
        assertThat(testSubject.findAll()).singleElement().usingRecursiveComparison().isEqualTo(defaultOrder());
    }

    @Test
    void testFindAll() {

        // prepare
        assertThat(testSubject.findAll()).isEmpty();

        testSubject.insert(defaultOrder());
        testSubject.insert(defaultOrder().toBuilder().id(new OrderId("O_XR76TZ2T")).build());

        // action
        var result = testSubject.findAll();

        // verify
        assertThat(result).hasSize(2);
        assertThat(result).element(0).usingRecursiveComparison().isEqualTo(defaultOrder());
        assertThat(result).element(1).usingRecursiveComparison().isEqualTo(defaultOrder().toBuilder()
                .id(new OrderId("O_XR76TZ2T"))
                .address(defaultAddress().toBuilder().id(2L).orderId(new OrderId("O_XR76TZ2T")).build())
                .build());
    }

    @Test
    void testGetById() {

        // prepare
        assertThat(testSubject.findAll()).isEmpty();
        testSubject.insert(defaultOrder());

        // action
        var result = testSubject.getById(defaultOrder().getId());

        // verify
        assertThat(result).hasValueSatisfying(order ->
                assertThat(order).usingRecursiveComparison().isEqualTo(defaultOrder()));
    }
}
