package org.hahlqy.taco.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hahlqy.taco.Ingredient;
import org.hahlqy.taco.data.IngredientRepository;
import org.hahlqy.taco.data.TacoRepository;
import org.hahlqy.taco.vo.Order;
import org.hahlqy.taco.vo.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Slf4j
public class DesignTacoController {

    @Autowired
    private  IngredientRepository ingredientRepos;

    @Autowired
    private TacoRepository tacoRepos;


    @GetMapping
    public String showDesignForm(Model model) {

//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO","Flour Tortilla", Ingredient.Type.WRAP),
//                new Ingredient("COTO","Corn Tortilla", Ingredient.Type.WRAP),
//                new Ingredient("GRBF","Ground Beef", Ingredient.Type.PROTEIN),
//                new Ingredient("CARN","Carnitas", Ingredient.Type.PROTEIN),
//                new Ingredient("TMTO","Dices Tomatoes", Ingredient.Type.VEGGIES),
//                new Ingredient("LETC","Lettuce", Ingredient.Type.VEGGIES),
//                new Ingredient("CHED","Cheddar", Ingredient.Type.CHEESE),
//                new Ingredient("JACK","Monterrey Jack", Ingredient.Type.CHEESE),
//                new Ingredient("SLSA","Salsa", Ingredient.Type.SAUCE),
//                new Ingredient("SRCR","Sour Cream", Ingredient.Type.SAUCE)
//        );
        List<Ingredient> ingredients = ingredientRepos.findAll();
        Ingredient.Type[] values = Ingredient.Type.values();
        for(Ingredient.Type type : values) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        model.addAttribute("order", new Order());

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(ingredient -> ingredient.getType() == type).collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco taco, Errors errors,
                                @ModelAttribute("order") Order order) {
        if(errors.hasErrors()) {
            return "design";
        }
        log.info("processing taco: {}", taco);

        Taco saved = tacoRepos.save(taco);
        order.setTaco(saved);
        return "redirect:/orders/current";
    }

}
