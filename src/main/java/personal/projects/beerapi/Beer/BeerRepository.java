package personal.projects.beerapi.Beer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BeerRepository {

    @Autowired private JdbcTemplate jdbcTemplate;
    private final String queryGetBeers = "  SELECT *                   " +
                                         "    FROM nodejs_beer.beers b " +
                                         "ORDER BY b.id                ";

    public List<Beer> getBeers() {
        List<Beer> beersList = jdbcTemplate.query(queryGetBeers, BeanPropertyRowMapper.newInstance(Beer.class));
        return beersList;
    }
    
}