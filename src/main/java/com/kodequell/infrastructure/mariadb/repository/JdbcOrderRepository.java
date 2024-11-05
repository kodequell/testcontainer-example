package com.kodequell.infrastructure.mariadb.repository;

import com.kodequell.domain.entity.Address;
import com.kodequell.domain.entity.Order;
import com.kodequell.domain.repository.OrderRepository;
import com.kodequell.domain.value.CustomerId;
import com.kodequell.domain.value.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.dao.support.DataAccessUtils.optionalResult;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void insert(Order order) {

        jdbcTemplate.update("INSERT INTO `order` (`id`, `customer_id`) VALUES (:id, :customerId)", Map.of(
                "id", order.getId().value(),
                "customerId", order.getCustomerId().value()
        ));

        jdbcTemplate.update(
                "INSERT INTO `address` (`order_id`, `first_name`, `last_name`, `street`, `zip_code`, `city`, `country`) " +
                        "VALUES (:orderId, :firstName, :lastName, :street, :zipCode, :city, :country)", Map.of(
                        "orderId", order.getId().value(),
                        "firstName", order.getAddress().getFirstName(),
                        "lastName", order.getAddress().getLastName(),
                        "street", order.getAddress().getStreet(),
                        "zipCode", order.getAddress().getZipCode(),
                        "city", order.getAddress().getCity(),
                        "country", order.getAddress().getCountry()
                ));
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("SELECT * FROM `order` ORDER BY `id` ASC", this::mapOrder);
    }

    @Override
    public Optional<Order> getById(final OrderId orderId) {

        return optionalResult(jdbcTemplate.query("SELECT * FROM `order` WHERE id = :id",
                Map.of("id", orderId.value()), this::mapOrder));
    }

    private Optional<Address> getAddressByOrderId(final OrderId orderId) {

        return optionalResult(jdbcTemplate.query(
                "SELECT * FROM `address` WHERE `order_id`= :orderId",
                Map.of("orderId", orderId.value()), (rs, num) ->
                        Address.builder()
                                .id(rs.getLong("id"))
                                .orderId(orderId)
                                .firstName(rs.getString("first_name"))
                                .lastName(rs.getString("last_name"))
                                .street(rs.getString("street"))
                                .zipCode(rs.getString("zip_code"))
                                .city(rs.getString("city"))
                                .country(rs.getString("country"))
                                .build()
        ));
    }

    private Order mapOrder(final ResultSet rs, final int num) throws SQLException {

        final var orderId = new OrderId(rs.getString("id"));

        final var result = new AtomicReference<>(Order.builder()
                .id(orderId)
                .customerId(new CustomerId(rs.getString("customer_id")))
                .build());

        getAddressByOrderId(orderId).ifPresent(address -> result.set(result.get().toBuilder().address(address).build()));

        return result.get();
    }
}
