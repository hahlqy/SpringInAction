package org.hahlqy.taco.service.impl;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import org.hahlqy.taco.service.OrderMessageService;
import org.hahlqy.taco.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderMessageService implements OrderMessageService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendOrder(Order order) {
        //设置消息发送的目的地址与消息内容
        jmsTemplate.send("tacoCloud.order.queue",
                session->session.createObjectMessage(order)
        );
    }
}
