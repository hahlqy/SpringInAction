package org.hahlqy.taco.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hahlqy.taco.web.api.IngredientApiController;
import org.hahlqy.taco.web.api.hateoas.IngredientModleAssembler;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class TacoModel extends RepresentationModel<TacoModel> {

    private String name;

    private Date createAt;

    private List<IngredientModel> ingredientModels;


    public TacoModel(Taco taco) {
        this.name = taco.getName();
        this.createAt = taco.getCreateAt();
        if(taco.getIngredients() != null && !taco.getIngredients().isEmpty()) {
            this.ingredientModels = taco.getIngredients().stream()
                    .map(Ingredient::new)
                    .map(new IngredientModleAssembler(IngredientApiController.class, IngredientModel.class)::toModel)
                    .toList();
        }
    }
}
