package org.hahlqy.taco.web.api;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.hahlqy.taco.data.mybatis.TacoIngredientMapper;
import org.hahlqy.taco.data.mybatis.TacoMapper;
import org.hahlqy.taco.vo.Ingredient;
import org.hahlqy.taco.vo.Taco;
import org.hahlqy.taco.vo.TacoModel;
import org.hahlqy.taco.web.api.hateoas.TacoModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/design",
        produces = "application/json")
@CrossOrigin("*")
@Slf4j
public class DesignTacoApiController {

    @Autowired
    private TacoMapper tacoMapper;
    @Autowired
    private TacoIngredientMapper tacoIngredientMapper;



    @GetMapping("/recent")
    public CollectionModel<Taco> recentTacos(@RequestParam int pageIndex,@RequestParam int pageSize) {
        List<Taco> tacoList = tacoMapper.getTacoList(pageIndex, pageSize);
        int recordSize = tacoMapper.countTaco();
        tacoList.forEach(taco -> {
            List<Ingredient> ingredientByTacoId = tacoIngredientMapper.findIngredientByTacoId(taco.getId());
            taco.setIngredients(
                    ingredientByTacoId.stream()
                            .map(Ingredient::getId)
                            .toList()
            );
        });
        tacoList.forEach(taco->{
            taco.add(linkTo(methodOn(DesignTacoApiController.class).getTacoById(taco.getId())).withSelfRel());
        });
        CollectionModel<Taco> collectionModel = CollectionModel.of(tacoList,
                linkTo(methodOn(DesignTacoApiController.class).recentTacos(pageIndex, pageSize)).withSelfRel());
        if(pageIndex>0){
            collectionModel.add(
                    linkTo(methodOn(DesignTacoApiController.class).recentTacos(pageIndex-1,pageSize)).withRel("last")
            );
        }
        if((pageIndex+1)*pageSize < recordSize){
            collectionModel.add(
                    linkTo(methodOn(DesignTacoApiController.class).recentTacos(pageIndex+1,pageSize)).withRel("next")
            );
        }
        return collectionModel;
    }

    @GetMapping("/recentTacoModel")
    public CollectionModel<TacoModel> recentTacoModels(){
        List<Taco> tacoList = tacoMapper.getTacoList(0, 5);
        tacoList.forEach(taco -> {
            List<Ingredient> ingredientByTacoId = tacoIngredientMapper.findIngredientByTacoId(taco.getId());
            taco.setIngredients(
                    ingredientByTacoId.stream()
                            .map(Ingredient::getId)
                            .toList()
            );
        });
        CollectionModel<TacoModel> collectionModel =
                new TacoModelAssembler(DesignTacoApiController.class, TacoModel.class).toCollectionModel(tacoList);
        collectionModel.add(
                linkTo(methodOn(DesignTacoApiController.class).recentTacoModels()).withRel("recent")
        );
        return collectionModel;
    }


    @GetMapping("/getTacoById/{id}")
    public ResponseEntity<Taco> getTacoById(@PathVariable Long id){
        Optional<Taco> taco = Optional.ofNullable(tacoMapper.getTacoById(id));
        return taco.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping(path = "/insert",
                consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco createTaco(@RequestBody Taco taco){
        log.info("Taco processed: {}", taco);
        taco.setCreateAt(new Date());
        tacoMapper.insertTaco(taco);
        taco.getIngredients().forEach(ingredient->{
            tacoIngredientMapper.insertTacoIngredient(taco.getId(),ingredient);
        });
        return taco;
    }

    @PutMapping(path = "/{designId}",
                consumes="application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Taco updateTaco(@PathVariable Long designId, @RequestBody Taco taco){
        taco.setId(designId);
        tacoMapper.updateTaco(taco);
        return taco;
    }


    @PatchMapping(path = "/{designId}",
            consumes="application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Taco updateTacoDetail(@PathVariable Long designId, @RequestBody Taco taco){
        Taco tacoById = tacoMapper.getTacoById(designId);
        if(StringUtils.isNotBlank(taco.getName())){
            tacoById.setName(taco.getName());
        }
        tacoMapper.updateTaco(tacoById);
        return taco;
    }


    @DeleteMapping(path = "/{designId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaco(@PathVariable Long designId){
        try{
            tacoMapper.deleteTaco(designId);
        }catch(EmptyResultDataAccessException e){
            log.info("Taco not found: {}", designId);
        }
    }
}
