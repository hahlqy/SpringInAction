package org.hahlqy.taco.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
    }
}
