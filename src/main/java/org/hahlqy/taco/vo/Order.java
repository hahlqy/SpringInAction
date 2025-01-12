package org.hahlqy.taco.vo;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private Long id;
    private Date createAt;
    @NotBlank(message = "Name is required")
    private String deliveryName;
    @NotBlank(message = "Street address is required")
    private String deliveryStreet;
    @NotBlank(message = "City is required")
    private String deliveryCity;
    @NotBlank(message = "State is required")
    private String deliveryState;
    @NotBlank(message = "Zip code  is required")
    private String deliveryZip;
    @NotBlank(message = "Not a valid Credit Card Number")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
            message="Must be MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0,message = "Invalid CVV")
    private String ccCVV;
    private Taco taco;

    private Date placedAt;

}
