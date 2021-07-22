package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
}
