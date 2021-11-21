package personal.projects.beerapi.Beer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerController {

    @Autowired private Environment env;
    @Autowired private BeerService beerService;

    @GetMapping(path = "/beerapi")
    public String home() {
        return "Welcome to Beer API from " + env.getProperty("api.environment");
    }

    @GetMapping(path = "/beerapi/read")
    public ResponseEntity<List<Beer>> read() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(beerService.getBeers());
        } catch(EmptyResultDataAccessException ex) {
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/beerapi/read/{beerId}")
    public ResponseEntity<Beer> read(@PathVariable(name = "beerId") Integer beerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(beerService.getBeer(beerId));
        } catch(EmptyResultDataAccessException ex) {
            return ResponseEntity.noContent().build();
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
}