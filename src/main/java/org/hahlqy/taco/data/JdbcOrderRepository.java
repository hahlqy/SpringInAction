package org.hahlqy.taco.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hahlqy.taco.vo.Order;
import org.hahlqy.taco.vo.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final SimpleJdbcInsert orderInsert;

    private final SimpleJdbcInsert orderTacoInsert;

    private final JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        this.orderTacoInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order_Tacos");
        this.jdbcTemplate = jdbcTemplate;
        objectMapper = new ObjectMapper();
    }


    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        Taco taco = order.getTaco();
        saveTacoToOrder(taco.getId(),orderId);
        return order;
    }

    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String,Object> values =
                objectMapper.convertValue(order, Map.class);
        values.put("placeAt", new Timestamp(order.getPlacedAt().getTime()));
        return orderInsert.executeAndReturnKey(values)
                .longValue();
    }

    private void saveTacoToOrder(long tacoId, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", tacoId);
        orderTacoInsert.execute(values);
    }

    @Override
    public Order findById(long id) {
        Order order  = jdbcTemplate.queryForObject("select id,deliveryName,deliveryStreet," +
                        "deliveryCity,deliveryState,deliveryZip,ccNumber,ccExpiration,ccCVV from taco_order where id = ?",
                new BeanPropertyRowMapper<Order>(Order.class), id
        );
        return order;
    }
}
