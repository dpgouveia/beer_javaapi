package personal.projects.beerapi.Beer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeerService {

    @Autowired private BeerRepository beerRepository;

    public List<Beer> getBeers() {
        return beerRepository.getBeers();
    }

    public Beer getBeer(Integer beerId) {
        return beerRepository.getBeer(beerId);
    }
    
}
