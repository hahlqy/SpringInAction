package org.hahlqy.taco.data;

import org.hahlqy.taco.Ingredient;

import java.util.List;


public interface IngredientRepository {
    List<Ingredient> findAll();

     Ingredient findOne(String id);

     Ingredient save(Ingredient ingredient);
}
