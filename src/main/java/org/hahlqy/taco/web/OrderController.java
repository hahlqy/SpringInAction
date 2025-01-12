package org.hahlqy.taco.web;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hahlqy.taco.data.OrderRepository;
import org.hahlqy.taco.data.mybatis.OrderMapper;
import org.hahlqy.taco.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/current")
    public String orderForm(Model model) {
        if(!model.containsAttribute("order")) {
            model.addAttribute("order", new Order());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if(errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order processed: " + order);
        orderRepository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @PostMapping("/getOrderDetail")
    @ResponseBody
    public Order processOrderDetail(@RequestBody Order order) {
        log.info("Order processed: " + order);
        return orderMapper.findOrderById(order.getId());
    }

    @PostMapping("/insert")
    @ResponseBody
    public Order insertIntoTacoOrder(@RequestBody Order order) {
        log.info("Order processed: " + order);
        order.setPlacedAt(new Date());
        orderMapper.insertOrder(order);
        return order;
    }
}
