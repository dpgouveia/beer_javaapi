package personal.projects.beerapi.Beer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @EnableAutoConfiguration
public class BeerController {

    @Autowired private Environment env;
    @Autowired private BeerRepository beerRepository;

    @GetMapping(path = "/beerapi/")
    public String home() {
        return "Welcome to Beer API from " + env.getProperty("api.environment");
    }

    @GetMapping(path = "/beerapi/read/")
    public List<Beer> read() {
        return beerRepository.getBeers();
    }
    
}