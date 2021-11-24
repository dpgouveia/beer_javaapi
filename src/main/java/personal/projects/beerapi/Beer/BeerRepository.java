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

    private final String queryGetBeer = " select *                   " +
                                        "   from nodejs_beer.beers b " +
                                        "  where b.id = ?            ";

    private final String queryInsertBeer = "insert into nodejs_beer.beers                   " +
                                           "            (name, tagline, description, image) " +
                                           "     values (?, ?, ?, ?)                        ";

    private final String queryUpdateBeer = "update nodejs_beer.beers                                 " +
                                           "   set name = ?, tagline = ?, description = ?, image = ? " +
                                           " where id = ?                                            ";

    private final String queryDeleteBeer = "delete                   " +
                                           "  from nodejs_beer.beers " +
                                           " where id = ?            ";


    public List<Beer> getBeers() {
        return jdbcTemplate.query(queryGetBeers, BeanPropertyRowMapper.newInstance(Beer.class));
    }

    public Beer getBeer(Integer beerId) {
        return jdbcTemplate.queryForObject(
                                    queryGetBeer, 
                                    (rs, rowNum) -> new Beer(rs.getInt("id"), rs.getString("name") , rs.getString("tagline"), rs.getString("description"), rs.getString("image"))
                                    , beerId);
    }

    public Integer createBeer(Beer beer) {
        return jdbcTemplate.update(queryInsertBeer, beer.getName(), beer.getTagline(), beer.getDescription(), beer.getImage());
    }

    public Integer updateBeer(Integer beerId, Beer beer) {
        return jdbcTemplate.update(queryUpdateBeer, beer.getName(), beer.getTagline(), beer.getDescription(), beer.getImage(), beerId);
    }

    public Integer deleteBeer(Integer beerId) {
        return jdbcTemplate.update(queryDeleteBeer, beerId);
    }
    
}