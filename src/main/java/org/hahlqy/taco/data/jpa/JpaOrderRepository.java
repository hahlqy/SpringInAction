package org.hahlqy.taco.data.jpa;


import org.hahlqy.taco.vo.jpa.JpaOrder;


public interface JpaOrderRepository {

    JpaOrder getOrderById(Long id);

    JpaOrder getOrderByDeliveryName(String name);


}
