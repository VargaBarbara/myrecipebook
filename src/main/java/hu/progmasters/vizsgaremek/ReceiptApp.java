package hu.progmasters.vizsgaremek;

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

    //TODO swagger
    //TODO postman
    //TODO column name
    //TODO tests
    //TODO optional new banner
    //TODO validation


}
