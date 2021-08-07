package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.RatingCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.RatingInfo;
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

    //User methods------------------------------------------------------------------------------------------------

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

    @PutMapping("/{toUpdateId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "updates one specific user account")
    @ApiResponse(responseCode = "201", description = "specific user account updated")
    public UserInfo updateUser(@PathVariable Integer toUpdateId, @RequestParam Integer loggedInUserId,
                               @RequestBody UserCreateUpdateCommand command) {
        return service.updateUser(toUpdateId, loggedInUserId, command);
    }

    @DeleteMapping("/{toDeleteId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes one specific user account")
    @ApiResponse(responseCode = "200", description = "specific user account deleted")
    public UserInfo deleteUser(@PathVariable Integer toDeleteId, @RequestParam Integer loggedInUserId) {
        return service.deleteUser(toDeleteId, loggedInUserId);
    }

    //Rating methods-------------------------------------------------------------------------------------------

    @PostMapping("/{userId}/ratings/{recipeId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "saves rating")
    @ApiResponse(responseCode = "201", description = "rating saved")
    public RatingInfo saveRating(@PathVariable Integer userId, @PathVariable Integer recipeId,
                                 @RequestBody RatingCreateUpdateCommand command){
        return service.saveOrUpdateRating(userId, recipeId, command);
    }

    @GetMapping("/{userId}/ratings/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns one specific rating")
    @ApiResponse(responseCode = "200", description = "specific rating returned")
    public RatingInfo findRatingByUserAndRecipe(@PathVariable Integer userId, @PathVariable Integer recipeId){
        return service.findRatingByUserAndRecipe(userId, recipeId);
    }

    @GetMapping("/{userId}/ratings/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns all ratings given by a specific user")
    @ApiResponse(responseCode = "200", description = "all ratings listed")
    public List<RatingInfo> findAllRatingsByUser(@PathVariable Integer userId){
        return service.findAllRatingsByUser(userId);
    }

    @DeleteMapping("/{userId}/ratings/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes one specific rating")
    @ApiResponse(responseCode = "200", description = "specific rating deleted")
    public RatingInfo deleteRating(@PathVariable Integer userId, @PathVariable Integer recipeId){
        return service.deleteRating(userId, recipeId);
    }

}
