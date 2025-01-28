package org.hahlqy.taco.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
public class IngredientModel extends RepresentationModel<IngredientModel> {

    private String name;

    private String type;

    public IngredientModel(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
