package org.hahlqy.taco.web.api;

import lombok.extern.slf4j.Slf4j;
import org.hahlqy.taco.data.mybatis.TacoMapper;
import org.hahlqy.taco.vo.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/design",
        produces = "application/json")
@CrossOrigin("*")
@Slf4j
public class DesignTacoApiController {

    @Autowired
    private TacoMapper tacoMapper;


    @GetMapping("/recent")
    public List<Taco> recentTacos(){
        return tacoMapper.getTacoList(0,12);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Taco> getTacoById(@PathVariable Long id){
        Optional<Taco> taco = Optional.ofNullable(tacoMapper.getTacoById(id));
        return taco.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping(value = "/insert",
                consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco createTaco(@RequestBody Taco taco){
        log.info("Taco processed: {}", taco);
        taco.setCreateAt(new Date());
        tacoMapper.insertTaco(taco);
        return taco;
    }
}
