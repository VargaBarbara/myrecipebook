package projects.VargaBarbara.myrecipebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projects.VargaBarbara.myrecipebook.dto.RatingCreateUpdateCommand;
import projects.VargaBarbara.myrecipebook.dto.RatingInfo;
import projects.VargaBarbara.myrecipebook.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Operations on ratings")
public class RatingController {

    public static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

    private UserService service;

    public RatingController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user/{userId}/recipe/{recipeId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "saves rating")
    @ApiResponse(responseCode = "201", description = "rating saved")
    public RatingInfo saveRating(@PathVariable Integer userId, @PathVariable Integer recipeId,
                                 @Valid @RequestBody RatingCreateUpdateCommand command) {
        LOGGER.info("POST request for saveRating with userId: " + userId + " with recipeId: "
                + recipeId + " and with body: " + command.toString());
        return service.saveOrUpdateRating(userId, recipeId, command);
    }

    @GetMapping("/user/{userId}/recipe/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns one specific rating")
    @ApiResponse(responseCode = "200", description = "specific rating returned")
    public RatingInfo findRatingByUserAndRecipe(@PathVariable Integer userId, @PathVariable Integer recipeId) {
        LOGGER.info("GET request for findRatingByUserAndRecipe with id: " + userId + " and with recipeId: " + recipeId);
        return service.findRatingByUserAndRecipe(userId, recipeId);
    }

    @GetMapping("/user/{userId}/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns all ratings given by a specific user")
    @ApiResponse(responseCode = "200", description = "all ratings listed")
    public List<RatingInfo> findAllRatingsByUser(@PathVariable Integer userId) {
        LOGGER.info("GET request for findAllRatingsByUser with userId: " + userId);
        return service.findAllRatingsByUser(userId);
    }

    @DeleteMapping("/user/{userId}/recipe/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes one specific rating")
    @ApiResponse(responseCode = "200", description = "specific rating deleted")
    public RatingInfo deleteRating(@PathVariable Integer userId, @PathVariable Integer recipeId) {
        LOGGER.info("DELETE request for deleteRating with userId: " + userId + " and with recipeId: " + recipeId);
        return service.deleteRating(userId, recipeId);
    }

}
