package org.hahlqy.taco.data;

import org.hahlqy.taco.vo.Order;

public interface OrderRepository {
    Order save(Order order);

    Order findById(long id);
}
