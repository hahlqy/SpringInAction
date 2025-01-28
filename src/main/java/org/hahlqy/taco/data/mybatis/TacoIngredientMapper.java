package org.hahlqy.taco.data.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hahlqy.taco.vo.Ingredient;

import java.util.List;

@Mapper
public interface TacoIngredientMapper {

    @Insert("insert into taco_ingredients (taco,ingredient) values (#{tacoId},#{ingredient})")
    void insertTacoIngredient(@Param("tacoId") Long tacoId, @Param("ingredient") String ingredient);

    @Select("select id,name,type from ingredient where id in (" +
            "select ingredient as id from taco_ingredients where taco=#{tacoId}" +
            ")")
    List<Ingredient> findIngredientByTacoId(@Param("tacoId") Long tacoId);
}
