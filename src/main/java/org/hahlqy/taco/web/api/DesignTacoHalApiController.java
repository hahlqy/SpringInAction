package org.hahlqy.taco.web.api;


import lombok.extern.slf4j.Slf4j;
import org.hahlqy.taco.data.mybatis.TacoIngredientMapper;
import org.hahlqy.taco.data.mybatis.TacoMapper;
import org.hahlqy.taco.vo.Taco;
import org.hahlqy.taco.vo.TacoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping(path = "/api/halTaco",
        produces = MediaTypes.HAL_JSON_VALUE)
@CrossOrigin("*")
@Slf4j
public class DesignTacoHalApiController {
    @Autowired
    private TacoMapper tacoMapper;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/traverson/getTacoById")
    public TacoModel traversonTaco(){
        Traverson traverson =
                new Traverson(URI.create("http://localhost:8080/api/halTaco/getTacoModelById"), MediaTypes.HAL_JSON);
        traverson.setRestOperations(restTemplate);
        ResponseEntity<TacoModel> entity = traverson.follow()
                .toEntity(TacoModel.class);

        return entity.getBody();
    }

    @GetMapping("/restAction/getEntity")
    public TacoModel restAction3(Long tacoId){
        Map<String,Object> params = new HashMap<>();
        params.put("id",tacoId);
        URI url = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/design/getTacoModelById")
                .build(params);

        ResponseEntity<TacoModel> entity = restTemplate.exchange("http://localhost:8080/api/design/getTacoModelById", HttpMethod.GET,
                new HttpEntity<>(MediaTypes.HAL_JSON), TacoModel.class);
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
