package org.hahlqy.taco.data.mybatis;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hahlqy.taco.vo.Taco;

import java.util.List;

@Mapper
public interface TacoMapper {

    @Select("select id,name,createAt from taco order by createAt desc  limit #{pageIndex},#{pageSize}")
    List<Taco> getTacoList(@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);

    @Select("select id,name from taco where id = #{id}")
    Taco getTacoById(@Param("id") Long id);

    void insertTaco(Taco taco);

    void updateTaco(Taco taco);

    @Delete("delete from taco where id=#{id}")
    void deleteTaco(@Param("id") Long id);

    @Select("select count(1) from taco")
    int countTaco();
}
