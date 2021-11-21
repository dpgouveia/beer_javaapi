package personal.projects.beerapi;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BeerApiApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()	
			.profiles("dev")
			//.profiles("qa")
			.sources(BeerApiApplication.class)
			.run(args);
	}

	@Bean ApplicationRunner applicationRunner(Environment environment) {
        return (args) -> {
			System.out.println("API Profile Environment: " + environment.getProperty("api.environment"));
        };
    }

}
