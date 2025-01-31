package org.hahlqy.taco.service.listener;


import org.hahlqy.taco.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsOrderListener {

    @JmsListener(destination = "tacoCloud.convert.order.queue")
    public void receieveOrder(final Order order) {
        System.out.println(order);
    }
}
