package org.hahlqy.taco.web.api.hateoas;

import org.hahlqy.taco.vo.Ingredient;
import org.hahlqy.taco.vo.IngredientModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class IngredientModleAssembler
            extends RepresentationModelAssemblerSupport<Ingredient, IngredientModel> {


    public IngredientModleAssembler(Class<?> controllerClass, Class<IngredientModel> resourceType) {
        super(controllerClass, resourceType);
    }


    @Override
    protected IngredientModel instantiateModel(Ingredient entity) {
        return new IngredientModel(entity);
    }

    @Override
    public IngredientModel toModel(Ingredient entity) {
        return createModelWithId(entity.getId(), entity);
    }
}
