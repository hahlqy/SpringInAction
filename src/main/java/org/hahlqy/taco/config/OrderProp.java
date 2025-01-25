package org.hahlqy.taco.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "taco.design")
public class OrderProp {

    @Min(value = 5,message = "must be between 5 and 25")
    @Max(value = 25,message = "must be between 5 and 25")
    private int pageSize = 20;
}
