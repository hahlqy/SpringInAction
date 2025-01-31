package org.hahlqy.taco.service.impl;



import org.hahlqy.taco.service.OrderReceiver;
import org.hahlqy.taco.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderReceiver implements OrderReceiver {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private MessageConverter messageConverter;

    @Override
    public Order receiveOrder() {
        return (Order) jmsTemplate.receiveAndConvert(
                "tacoCloud.convert.order.queue");
    }
}
