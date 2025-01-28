package org.hahlqy.taco.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hahlqy.taco.Ingredient;
import org.hahlqy.taco.config.OrderProp;
import org.hahlqy.taco.data.IngredientRepository;
import org.hahlqy.taco.data.TacoRepository;
import org.hahlqy.taco.data.mybatis.TacoMapper;
import org.hahlqy.taco.vo.Order;
import org.hahlqy.taco.vo.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private TacoMapper tacoMapper;

    @Autowired
    private OrderProp orderProp;




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

    /**
     * @AuthenticationPrincipal获取登录用户信息
     * @param taco
     * @param errors
     * @param order
     * @param user
     * @return
     */
//    @PostMapping
//    public String processDesign(@Valid @ModelAttribute("design") Taco taco, Errors errors,
//                                @ModelAttribute("order") Order order ,@AuthenticationPrincipal User user) {
//        if(errors.hasErrors()) {
//            return "design";
//        }
//        log.info("processing taco: {}", taco);
//        log.info("user: {}", user);
//        Taco saved = tacoRepos.save(taco);
//        order.setTaco(saved);
//        return "redirect:/orders/current";
//    }

//    /**
//     * Authentication获取登录用户信息
//     * @param taco
//     * @param errors
//     * @param order
//     * @return
//     */
//    @PostMapping
//    public String processDesign(@Valid @ModelAttribute("design") Taco taco, Errors errors,
//                                @ModelAttribute("order") Order order , SessionStatus status, Authentication auth) {
//        if(errors.hasErrors()) {
//            return "design";
//        }
//        log.info("processing taco: {}", taco);
//        log.info("user: {}", auth.getPrincipal());
//        Taco saved = tacoRepos.save(taco);
//        order.setTaco(saved);
//        return "redirect:/orders/current";
//    }

//    /**
//     * SecurityContextHolder获取登录用户信息
//     * @param taco
//     * @param errors
//     * @param order
//     * @return
//     */
    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco taco, Errors errors,
                                @ModelAttribute("order") Order order ) {
        if(errors.hasErrors()) {
            return "design";
        }
        log.info("processing taco: {}", taco);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("user: {}", authentication.getPrincipal());
        Taco saved = tacoRepos.save(taco);
        order.setTaco(saved);
        return "redirect:/orders/current";
    }

    @GetMapping("/getTacos")
    @ResponseBody
    public List<Taco> getTacos() {
        return tacoMapper.getTacoList(0,orderProp.getPageSize());
    }

}
