package personal.projects.beerapi.Beer;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Erro durante o processamento do enpoint BeerApi.read(). Causa: " + ex.getMessage()));
        }
    }

    @GetMapping(path = "/beerapi/read/{beerId}")
    public ResponseEntity<String> read(@PathVariable(name = "beerId") Integer beerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(beerService.getBeer(beerId)));
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson("Beer ID " + beerId + " not found!"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Erro durante o processamento do enpoint BeerApi.read(beerId). Causa: " + ex.getMessage()));
        }
    }

    @PostMapping(path = "/beerapi/create/")
    public ResponseEntity<String> create(@RequestBody Beer beer) {
        try {
            Integer rowsAffected = beerService.createBeer(beer);
            if(rowsAffected > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(beer.getName() + " beer created successfully!" ));
            }

            throw new IllegalStateException("Erro desconhecido durante o processamento endpoint BeerApi.createBeer(beer)");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Erro durante o processamento do enpoint BeerApi.create(beer). Causa: " + ex.getMessage()));
        }
    }

    @PutMapping(path = "/beerapi/update/{beerId}")
    public ResponseEntity<String> update(@PathVariable(name = "beerId") Integer beerId, @RequestBody Beer beer) {
        try {

            beerService.getBeer(beerId); // check if beer id exists in database
            Integer rowsAffected = beerService.updateBeer(beerId, beer);
            if(rowsAffected > 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } 

            throw new IllegalStateException("Erro desconhecido durante o processamento endpoint BeerApi.updateBeer(beerId, beer)");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson("Beer ID " + beerId + " not found!"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Erro durante o processamento do endpoint BeerApi.updateBeer(beerId, beer). Causa: " + ex.getMessage()));
        }
    }

    @DeleteMapping(path = "/beerapi/delete/{beerId}")
    public ResponseEntity<String> update(@PathVariable(name = "beerId") Integer beerId) {
        try {

            beerService.getBeer(beerId); // check if beer id exists in database
            Integer rowsAffected = beerService.deleteBeer(beerId);
            if(rowsAffected > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Beer ID " + beerId + " deleted from database successfully!"));
            } 

            throw new IllegalStateException("Erro desconhecido durante o processamento endpoint BeerApi.deleteBeer(beerId)");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson("Beer ID " + beerId + " not found!"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Erro durante o processamento do endpoint BeerApi.deleteBeer(beerId). Causa: " + ex.getMessage()));
        }
    }

}