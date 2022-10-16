package projects.VargaBarbara.myrecipebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecipeApp {

    public static void main(String[] args) {
        SpringApplication.run(RecipeApp.class, args);
    }

    @Bean
    ModelMapper modelMapper() {return new ModelMapper();}

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    //TODO RecipesService tests
    //TODO RatingsService tests
    //TODO docker
    //TODO outsource Rating methods from UserController

}
