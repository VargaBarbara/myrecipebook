package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.ReceiptCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.ReceiptInfo;
import hu.progmasters.vizsgaremek.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    //TODO log

    private UserService service;

    public ReceiptController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReceiptInfo saveReceipt(@RequestParam Integer userId, @RequestBody ReceiptCreateUpdateCommand command) {
        LocalDate creationDate = LocalDate.now();
        return service.saveReceipt(userId, creationDate, command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReceiptInfo> findAllReceipts() {
        return service.findAllReceipts();
    }

    @GetMapping("/receipt/{receiptId}")
    @ResponseStatus(HttpStatus.OK)
    public ReceiptInfo findReceiptById(@PathVariable Integer receiptId) {
        return service.findReceiptById(receiptId);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReceiptInfo> findReceiptsByUser(@PathVariable Integer userId) {
        return service.findReceiptsByUser(userId);
    }

    @PutMapping("/{receiptId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReceiptInfo updateReceipt(@PathVariable Integer receiptId,
                                     @RequestParam Integer userId,
                                     @RequestBody ReceiptCreateUpdateCommand command) {
        LocalDate editDate = LocalDate.now();
        return service.updateReceipt(receiptId, userId, editDate, command);
    }

    @DeleteMapping("/{receiptId}")
    @ResponseStatus(HttpStatus.OK)
    public ReceiptInfo deleteReceipt(@PathVariable Integer receiptId, @RequestParam Integer userId) {
        return service.deleteReceipt(receiptId, userId);
    }

}
