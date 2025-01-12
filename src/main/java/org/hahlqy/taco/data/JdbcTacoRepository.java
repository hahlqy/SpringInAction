package org.hahlqy.taco.data;

import org.hahlqy.taco.Ingredient;
import org.hahlqy.taco.vo.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


@Repository
public class JdbcTacoRepository implements TacoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        taco.getIngredients().forEach(ingredient -> {
            saveIngredients(ingredient,tacoId);
        });
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreateAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name,createAt) values (?,?)",
                Types.VARCHAR, Types.TIMESTAMP
        );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = preparedStatementCreatorFactory.newPreparedStatementCreator(
                Arrays.asList(taco.getName(), new Timestamp(taco.getCreateAt().getTime()))
        );
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, key);
        return Objects.requireNonNull(key.getKey()).longValue();
    }

    private void saveIngredients(String ingredient,long tacoId) {
        jdbcTemplate.update(
                "insert into Taco_Ingredients (taco,ingredient) values (?,?)",
                tacoId, ingredient
        );
    }
}
