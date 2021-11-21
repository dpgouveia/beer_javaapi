package personal.projects.beerapi.Beer;

import java.io.Console;

import com.google.gson.Gson;
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
    private final Gson gson = new Gson();

    @GetMapping(path = "/beerapi")
    public String home() {
        return "Welcome to Beer API from " + env.getProperty("api.environment");
    }

    @GetMapping(path = "/beerapi/read")
    public ResponseEntity<String> read() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(beerService.getBeers()));
        } catch(EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson("List of beer is empty!"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Erro durante o processamento do enpoint BeerApi.read(). Causa: " + ex.getMessage()));
        }
    }

    @GetMapping(path = "/beerapi/read/{beerId}")
    public ResponseEntity<String> read(@PathVariable(name = "beerId") Integer beerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(beerService.getBeer(beerId)));
        } catch(EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson("Beer not found with id " + beerId));
        }
        catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Erro durante o processamento do enpoint BeerApi.read(beerId). Causa: " + ex.getMessage()));
        }
    }
    
}