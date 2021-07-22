package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.service.ReceiptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private ReceiptService service;

    public ReceiptController(ReceiptService service) {
        this.service = service;
    }

}
