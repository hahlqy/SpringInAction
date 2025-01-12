package org.hahlqy.taco.data.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hahlqy.taco.vo.Order;

@Mapper
public interface OrderMapper {

    @Select("select * from taco_order where id =#{id}")
    Order findOrderById(@Param("id") long id);

    void insertOrder(Order order);

}
