package org.hahlqy.taco.web.api;


import org.hahlqy.taco.service.OrderMessageService;
import org.hahlqy.taco.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderApiController {

    @Autowired
    private OrderMessageService orderMessageService;


    @PostMapping("/send")
    public void sendOrder(Order order) {
        orderMessageService.sendOrder(order);
    }
}
