package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.UserCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.UserInfo;
import hu.progmasters.vizsgaremek.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Operations on users")
public class UserController {

    //TODO log

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    //User methods

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a user account")
    @ApiResponse(responseCode = "201", description = "user account has been created")
    public UserInfo saveUser(@RequestBody UserCreateUpdateCommand command) {
        return service.saveUser(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "lists all users")
    @ApiResponse(responseCode = "200", description = "all users listed")
    public List<UserInfo> findAllUsers() {
        return service.findAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns one specific user")
    @ApiResponse(responseCode = "200", description = "specific user returned")
    public UserInfo findUserById(@PathVariable Integer id) {
        return service.findUserById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "updates one specific user account")
    @ApiResponse(responseCode = "201", description = "specific user account updated")
    public UserInfo updateUser(@PathVariable Integer id, @RequestParam Integer userId, @RequestBody UserCreateUpdateCommand command) {
        return service.updateUser(id, userId, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes one specific user account")
    @ApiResponse(responseCode = "200", description = "specific user account deleted")
    public UserInfo deleteUser(@PathVariable Integer id, @RequestParam Integer userId) {
        return service.deleteUser(id, userId);
    }




    //Rating methods TODO

}
