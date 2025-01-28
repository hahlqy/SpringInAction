package org.hahlqy.taco.web.api.hateoas;


import org.hahlqy.taco.vo.Taco;
import org.hahlqy.taco.vo.TacoModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.core.SpringAffordanceBuilder;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class TacoModelAssembler
                extends RepresentationModelAssemblerSupport<Taco,TacoModel> {
    public TacoModelAssembler(Class<?> controllerClass, Class<TacoModel> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    protected TacoModel instantiateModel(Taco entity) {
        return new TacoModel(entity);
    }

    @Override
    public TacoModel toModel(Taco entity) {
        return createModelWithId(entity.getId(), entity).add(
                Link.of(SpringAffordanceBuilder.DISCOVERER.getMapping(this.getControllerClass())+"/getTacoById/{id}")
                        .expand(entity.getId())
                        .withRel("findTacoById")
        );

    }



}
