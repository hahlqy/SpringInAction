package org.hahlqy.taco.web.api;


import lombok.extern.slf4j.Slf4j;
import org.hahlqy.taco.data.mybatis.IngredientMapper;
import org.hahlqy.taco.vo.Ingredient;
import org.hahlqy.taco.vo.IngredientModel;
import org.hahlqy.taco.web.api.hateoas.IngredientModleAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/ingredient",
                produces = "application/json")
@CrossOrigin("*")
@Slf4j
public class IngredientApiController {

    @Autowired
    private IngredientMapper ingredientMapper;

    @GetMapping(path = "/getAllIngredients")
    public CollectionModel<IngredientModel> getIngredients() {
        List<Ingredient> ingredients = ingredientMapper.selectAllIngredients();
        return  new IngredientModleAssembler(IngredientApiController.class, IngredientModel.class).toCollectionModel(ingredients);
    }

    @GetMapping(path = "/{id}")
    public IngredientModel getIngredientById(@PathVariable("id") String id) {
        return new IngredientModel(ingredientMapper.selectIngredientById(id));
    }
}
