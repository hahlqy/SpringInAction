package org.hahlqy.taco.web.api;


import org.hahlqy.taco.service.OrderMessageService;
import org.hahlqy.taco.service.OrderReceiver;
import org.hahlqy.taco.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderApiController {

    @Autowired
    private OrderMessageService orderMessageService;

    @Autowired
    private OrderReceiver orderReceiver;


    @PostMapping("/send")
    public void sendOrder(Order order) {
        orderMessageService.sendOrder(order);
    }

    @PostMapping("/sendWebOrder")
    public void sendWebOrder(@RequestBody Order order) {
        orderMessageService.sendOrderWithSource(order);
    }

    @GetMapping("/getOrder")
    public Order getOrder() {
        return orderReceiver.receiveOrder();
    }
}
