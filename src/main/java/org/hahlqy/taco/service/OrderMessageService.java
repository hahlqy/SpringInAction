package org.hahlqy.taco.service;

import org.hahlqy.taco.vo.Order;

public interface OrderMessageService {

    void sendOrder(Order order);
}
