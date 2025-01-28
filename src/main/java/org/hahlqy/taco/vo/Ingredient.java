package org.hahlqy.taco.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Ingredient {

    private String id;

    private String name;

    private String type;

    Ingredient(String id){
        this.id = id;
    }

}
