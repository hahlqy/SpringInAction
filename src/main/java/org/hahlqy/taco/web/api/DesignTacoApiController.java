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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

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
    @Autowired
    private RestTemplate restTemplate;



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
        if(taco.getIngredients()!=null && !taco.getIngredients().isEmpty()){
            taco.getIngredients().forEach(ingredient->{
                tacoIngredientMapper.insertTacoIngredient(taco.getId(),ingredient);
            });
        }
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

    @GetMapping("/restAction/get")
    public Taco restAction(Long tacoId){
        return restTemplate.getForObject("http://localhost:8080/api/design/getTacoById/{id}"
                , Taco.class, tacoId);
    }

    @GetMapping("/restAction/uri")
    public Taco restAction2(Long tacoId){
        Map<String,Object> params = new HashMap<>();
        params.put("id",tacoId);
        URI url = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/design/getTacoById/{id}")
                .build(params);

        return restTemplate.getForObject(url,Taco.class);
    }



    @PutMapping(path = "/restAction/put/{designTacoId}",
                consumes = "application/json")
    public void restAction4(@PathVariable Long designTacoId, @RequestBody Taco taco){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Taco> tacoHttpEntity = new HttpEntity<>(taco, httpHeaders);
        restTemplate.put("http://localhost:8080/api/design/{id}"
                , tacoHttpEntity, designTacoId);
    }

    @DeleteMapping("/restAction/{tacoId}")
    public void restAction5(@PathVariable Long tacoId){
        restTemplate.delete("http://localhost:8080/api/design/{id}"
                , tacoId );
    }

    @PostMapping("/restAction/insert")
    public Taco insertRestAction(@RequestBody Taco taco){
        Taco taco1 = restTemplate.postForObject("http://localhost:8080/api/design/insert",
                taco, Taco.class);
        return taco1;

    }

    @GetMapping("/traverson/getTacoById")
    public Taco traversonTaco(){
        Traverson traverson =
                new Traverson(URI.create("http://localhost:8080/api/design/getTacoModelById"), MediaType.APPLICATION_JSON);
        traverson.setRestOperations(restTemplate);
        ResponseEntity<Taco> entity = traverson.follow()
                .toEntity(Taco.class);
        return null;
    }

    @GetMapping("/restAction/getEntity")
    public Taco restAction3(Long tacoId){
        Map<String,Object> params = new HashMap<>();
        params.put("id",tacoId);
        URI url = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/design/getTacoModelById")
                .build(params);

        ResponseEntity<Taco> entity = restTemplate.exchange("http://localhost:8080/api/design/getTacoModelById", HttpMethod.GET,
                new HttpEntity<>(MediaTypes.HAL_JSON), Taco.class);
//        ResponseEntity<Taco> entity = restTemplate.getForEntity(url, Taco.class);
        log.info("Taco: {}", entity);

        return entity.getBody();
    }

    @GetMapping("/getTacoModelById")
    public TacoModel getTacoModelById(){
        Long id = 8L;
        Optional<Taco> taco = Optional.ofNullable(tacoMapper.getTacoById(id));
        return taco.map(value -> new TacoModel(value).add(linkTo(methodOn(DesignTacoApiController.class).getTacoModelById()).withSelfRel()))
                .orElse(null);
    }

}
