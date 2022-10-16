package projects.VargaBarbara.myrecipebook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.VargaBarbara.myrecipebook.dto.RatingCreateUpdateCommand;
import projects.VargaBarbara.myrecipebook.dto.RatingInfo;
import projects.VargaBarbara.myrecipebook.dto.UserCreateUpdateCommand;
import projects.VargaBarbara.myrecipebook.dto.UserInfo;
import projects.VargaBarbara.myrecipebook.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Operations on users")
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    //User methods------------------------------------------------------------------------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a user account")
    @ApiResponse(responseCode = "201", description = "user account has been created")
    public UserInfo saveUser(@Valid @RequestBody UserCreateUpdateCommand command) {
        LOGGER.info("POST request for saveUser with body: " + command.toString());
        return service.saveUser(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "lists all users")
    @ApiResponse(responseCode = "200", description = "all users listed")
    public List<UserInfo> findAllUsers() {
        LOGGER.info("GET request for findAllUsers");
        return service.findAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns one specific user")
    @ApiResponse(responseCode = "200", description = "specific user returned")
    public UserInfo findUserById(@PathVariable Integer id) {
        LOGGER.info("GET request for findUserById with id: " + id);
        return service.findUserById(id);
    }

    @PutMapping("/{toUpdateId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "updates one specific user account")
    @ApiResponse(responseCode = "201", description = "specific user account updated")
    public UserInfo updateUser(@PathVariable Integer toUpdateId, @RequestParam Integer loggedInUserId,
                               @Valid @RequestBody UserCreateUpdateCommand command) {
        LOGGER.info("PUT request for updateUser with toUpdateId: " + toUpdateId
                + " with loggedInUserId: " + loggedInUserId + " and with body: " + command.toString());
        return service.updateUser(toUpdateId, loggedInUserId, command);
    }

    @DeleteMapping("/{toDeleteId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes one specific user account")
    @ApiResponse(responseCode = "200", description = "specific user account deleted")
    public UserInfo deleteUser(@PathVariable Integer toDeleteId, @RequestParam Integer loggedInUserId,
                               @RequestParam(defaultValue = "${default.value.delete.recipes}") Boolean deleteRecipes) {
        LOGGER.info("DELETE request for deleteUser with toDeleteId: " + toDeleteId
                + " with loggedInUserId: " + loggedInUserId + " and with deleteRecipes: " + deleteRecipes);
        return service.deleteUser(toDeleteId, loggedInUserId, deleteRecipes);
    }

    //Rating methods-------------------------------------------------------------------------------------------

    @PostMapping("/{userId}/ratings/{recipeId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "saves rating")
    @ApiResponse(responseCode = "201", description = "rating saved")
    public RatingInfo saveRating(@PathVariable Integer userId, @PathVariable Integer recipeId,
                                 @Valid @RequestBody RatingCreateUpdateCommand command){
        LOGGER.info("POST request for saveRating with userId: " + userId + " with recipeId: "
                + recipeId + " and with body: " + command.toString());
        return service.saveOrUpdateRating(userId, recipeId, command);
    }

    @GetMapping("/{userId}/ratings/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns one specific rating")
    @ApiResponse(responseCode = "200", description = "specific rating returned")
    public RatingInfo findRatingByUserAndRecipe(@PathVariable Integer userId, @PathVariable Integer recipeId){
        LOGGER.info("GET request for findRatingByUserAndRecipe with id: " + userId + " and with recipeId: " + recipeId);
        return service.findRatingByUserAndRecipe(userId, recipeId);
    }

    @GetMapping("/{userId}/ratings/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns all ratings given by a specific user")
    @ApiResponse(responseCode = "200", description = "all ratings listed")
    public List<RatingInfo> findAllRatingsByUser(@PathVariable Integer userId){
        LOGGER.info("GET request for findAllRatingsByUser with userId: " + userId);
        return service.findAllRatingsByUser(userId);
    }

    @DeleteMapping("/{userId}/ratings/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes one specific rating")
    @ApiResponse(responseCode = "200", description = "specific rating deleted")
    public RatingInfo deleteRating(@PathVariable Integer userId, @PathVariable Integer recipeId){
        LOGGER.info("DELETE request for deleteRating with userId: " + userId + " and with recipeId: " + recipeId );
        return service.deleteRating(userId, recipeId);
    }

}
