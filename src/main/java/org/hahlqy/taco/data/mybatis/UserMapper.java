package org.hahlqy.taco.data.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hahlqy.taco.vo.User;

@Mapper
public interface UserMapper {

    @Select("select * from users where username =#{name}")
    User findByUserName(@Param("name") String username);
}
