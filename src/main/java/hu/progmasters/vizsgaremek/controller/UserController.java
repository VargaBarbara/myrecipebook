package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.UserCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.UserInfo;
import hu.progmasters.vizsgaremek.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    //TODO log

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    //User methods

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfo saveUser(@RequestBody UserCreateUpdateCommand command) {
        return service.saveUser(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserInfo> findAllUsers() {
        return service.findAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfo findUserById(@PathVariable Integer id) {
        return service.findUserById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfo updateUser(@PathVariable Integer id, @RequestParam Integer userId, @RequestBody UserCreateUpdateCommand command) {
        return service.updateUser(id, userId, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfo deleteUser(@PathVariable Integer id, @RequestParam Integer userId) {
        return service.deleteUser(id, userId);
    }




    //Rating methods TODO

}
