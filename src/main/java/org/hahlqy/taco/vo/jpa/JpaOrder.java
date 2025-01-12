package org.hahlqy.taco.vo.jpa;


import lombok.Data;

import java.util.Date;

@Data
public class JpaOrder {


    private Long id;
    private Date placeAt;
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
}
