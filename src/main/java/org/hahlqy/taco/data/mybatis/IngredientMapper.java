package org.hahlqy.taco.data.mybatis;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.hahlqy.taco.vo.Ingredient;

import java.util.List;

@Mapper
public interface IngredientMapper {

    @Select("select * from ingredient")
    List<Ingredient> selectAllIngredients();

    @Select("select * from ingredient where id = #{id}")
    Ingredient selectIngredientById(String id);
}
