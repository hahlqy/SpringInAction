package org.hahlqy.taco.vo;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Taco {
    private Long id;
    private Date createAt;
    @NotNull
    @Size(min=5,message = "Name must be at least 5 characters long")
    private String name;
    @Size(min = 1,message = "Your must choose at least 1 ingredient ")
    private List<String> ingredients;
}
