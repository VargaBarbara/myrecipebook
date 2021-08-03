package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.service.ReceiptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private ReceiptService service;

    public UserController(ReceiptService service) {
        this.service = service;
    }
}
