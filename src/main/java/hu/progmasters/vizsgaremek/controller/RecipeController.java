package hu.progmasters.vizsgaremek.controller;

import hu.progmasters.vizsgaremek.dto.RecipeCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.RecipeInfo;
import hu.progmasters.vizsgaremek.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Operations on recipes")
public class RecipeController {

    //TODO log

    private UserService service;

    public RecipeController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a recipe")
    @ApiResponse(responseCode = "201", description = "recipe has been created")
    public RecipeInfo saveRecipe(@RequestParam Integer userId, @Valid @RequestBody RecipeCreateUpdateCommand command) {
        LocalDate creationDate = LocalDate.now();
        return service.saveRecipe(userId, creationDate, command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "lists all recipes")
    @ApiResponse(responseCode = "200", description = "all recipes listed")
    public List<RecipeInfo> findAllRecipes() {
        return service.findAllRecipes();
    }

    @GetMapping("/recipe/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns one specific recipe")
    @ApiResponse(responseCode = "200", description = "specific recipe returned")
    public RecipeInfo findRecipeById(@PathVariable Integer recipeId) {
        return service.findRecipeById(recipeId);
    }

    @GetMapping("/recipe/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns one specific recipe")
    @ApiResponse(responseCode = "200", description = "specific recipe returned")
    public RecipeInfo findRandomRecipe() {
        return service.findRandomRecipe();
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns all recipes with user id given")
    @ApiResponse(responseCode = "200", description = "all recipes of the given user listed")
    public List<RecipeInfo> findRecipesByUser(@PathVariable Integer userId) {
        return service.findRecipesByUser(userId);
    }

    @PutMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "updates one specific recipe")
    @ApiResponse(responseCode = "201", description = "specific recipe updated")
    public RecipeInfo updateRecipe(@PathVariable Integer recipeId,
                                   @RequestParam Integer userId,
                                   @Valid @RequestBody RecipeCreateUpdateCommand command) {
        LocalDate editDate = LocalDate.now();
        return service.updateRecipe(recipeId, userId, editDate, command);
    }

    @DeleteMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "deletes one specific recipe")
    @ApiResponse(responseCode = "200", description = "specific recipe deleted")
    public RecipeInfo deleteRecipe(@PathVariable Integer recipeId, @RequestParam Integer userId) {
        return service.deleteRecipe(recipeId, userId);
    }

}
