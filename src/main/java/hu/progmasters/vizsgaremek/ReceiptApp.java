package hu.progmasters.vizsgaremek;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReceiptApp {

    public static void main(String[] args) {
        SpringApplication.run(ReceiptApp.class, args);
    }

    @Bean
    ModelMapper modelMapper() {return new ModelMapper();}

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    //TODO swagger - only rating left (for domains too?)
    //TODO postman - only rating left
    //TODO column name
    //TODO tests
    //TODO optional new banner
    //TODO validation
    //TODO jogosultságok
    //TODO adott email címmel regisztráltak-e már


}
