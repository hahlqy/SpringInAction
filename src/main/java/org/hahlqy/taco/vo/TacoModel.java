package org.hahlqy.taco.vo;

import lombok.Data;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;


@Data
@ToString
public class TacoModel extends RepresentationModel<TacoModel> {

    private String name;

    private Date createAt;

    private List<String> ingredients;


    public TacoModel(Taco taco) {
        this.name = taco.getName();
        this.ingredients = taco.getIngredients();
        this.createAt = taco.getCreateAt();
    }
}
