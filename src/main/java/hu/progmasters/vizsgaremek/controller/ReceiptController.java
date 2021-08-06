package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.ReceiptCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.ReceiptInfo;
import hu.progmasters.vizsgaremek.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/receipts")
@Tag(name = "Operations on receipts")
public class ReceiptController {

    //TODO log

    private UserService service;

    public ReceiptController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a receipt")
    @ApiResponse(responseCode = "201", description = "receipt has been created")
    public ReceiptInfo saveReceipt(@RequestParam Integer userId, @RequestBody ReceiptCreateUpdateCommand command) {
        LocalDate creationDate = LocalDate.now();
        return service.saveReceipt(userId, creationDate, command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "lists all receipts")
    @ApiResponse(responseCode = "200", description = "all receipts listed")
    public List<ReceiptInfo> findAllReceipts() {
        return service.findAllReceipts();
    }

    @GetMapping("/receipt/{receiptId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns one specific receipt")
    @ApiResponse(responseCode = "200", description = "specific receipt returned")
    public ReceiptInfo findReceiptById(@PathVariable Integer receiptId) {
        return service.findReceiptById(receiptId);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns all receipts with user id given")
    @ApiResponse(responseCode = "200", description = "all receipts of the given user listed")
    public List<ReceiptInfo> findReceiptsByUser(@PathVariable Integer userId) {
        return service.findReceiptsByUser(userId);
    }

    @PutMapping("/{receiptId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "updates one specific receipt")
    @ApiResponse(responseCode = "201", description = "specific receipt updated")
    public ReceiptInfo updateReceipt(@PathVariable Integer receiptId,
                                     @RequestParam Integer userId,
                                     @RequestBody ReceiptCreateUpdateCommand command) {
        LocalDate editDate = LocalDate.now();
        return service.updateReceipt(receiptId, userId, editDate, command);
    }

    @DeleteMapping("/{receiptId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes one specific receipt")
    @ApiResponse(responseCode = "200", description = "specific receipt deleted")
    public ReceiptInfo deleteReceipt(@PathVariable Integer receiptId, @RequestParam Integer userId) {
        return service.deleteReceipt(receiptId, userId);
    }

}
