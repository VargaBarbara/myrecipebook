package hu.progmasters.vizsgaremek.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import hu.progmasters.vizsgaremek.dto.*;
import hu.progmasters.vizsgaremek.exceptionhandling.ValidationError;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    UserCreateUpdateCommand cruellaDeVil1 = new UserCreateUpdateCommand
            ("CruellaDeVil1", "cruella.de.vil101@evil.com");
    UserCreateUpdateCommand snowWhite2 = new UserCreateUpdateCommand
            ("SnowWhite2", "snow.white2@good.com");
    UserCreateUpdateCommand yarnFairy3 = new UserCreateUpdateCommand
            ("YarnFairy3", "yarn.fairy3@good.com");
    UserCreateUpdateCommand dracula4 = new UserCreateUpdateCommand
            ("Dracula4", "dracula4@whatever.com");

    String beautyBeast1Preparation = "Tüzes borral teli pohár ontja bíbor illatát. " +
            "Végül még teázni sikk, édességhez jólesik.";
    String beautyBeast1Note = "Szörnyen jó, bon appetit!";
    String breadedDrawer2Preparation = "Kirántott fiók recept! " +
            "Végy egy jókora fiókot és könnyed laza mozdulattal rántsd ki.";
    String breadedDrawer2Note = "Omlósabb lesz, ha szúval előfűszerezzük.";
    String hunterGames3Preparation = "Vadászvacsora! Vadászd össze a kamra tartalmát, " +
            "ízlésesen helyezd egy tálcára és fogyaszd egészséggel.";
    String hunterGames3Note = "Csak ehető dolgokra vadássz!";
    String shakeTheMilk4Preparation = "Milkshake 2 személyre! Önts 4 dl tejet egy shakerbe, " +
            "majd 15 percig lendületes mozdulatokkal rázd össze.";
    String shakeTheMilk4Note = "Riszálom úgyis-úgyis!";

    RecipeCreateUpdateCommand firstRecipe = new RecipeCreateUpdateCommand(beautyBeast1Preparation, beautyBeast1Note);
    RecipeCreateUpdateCommand secondRecipe = new RecipeCreateUpdateCommand(breadedDrawer2Preparation, breadedDrawer2Note);
    RecipeCreateUpdateCommand thirdRecipe = new RecipeCreateUpdateCommand(hunterGames3Preparation, hunterGames3Note);
    RecipeCreateUpdateCommand fourthRecipe = new RecipeCreateUpdateCommand(shakeTheMilk4Preparation, shakeTheMilk4Note);

    RatingCreateUpdateCommand firstUserForFirstRecipe = new RatingCreateUpdateCommand(1);
    RatingCreateUpdateCommand firstUserForSecondRecipe = new RatingCreateUpdateCommand(2);
    RatingCreateUpdateCommand firstUserForThirdRecipe = new RatingCreateUpdateCommand(3);
    RatingCreateUpdateCommand secondUserForFirstRecipe = new RatingCreateUpdateCommand(4);
    RatingCreateUpdateCommand secondUserForSecondRecipe = new RatingCreateUpdateCommand(5);
    RatingCreateUpdateCommand secondUserForThirdRecipe = new RatingCreateUpdateCommand(6);
    RatingCreateUpdateCommand thirdUserForFirstRecipe = new RatingCreateUpdateCommand(7);
    RatingCreateUpdateCommand thirdUserForSecondRecipe = new RatingCreateUpdateCommand(8);
    RatingCreateUpdateCommand thirdUserForThirdRecipe = new RatingCreateUpdateCommand(9);
    RatingCreateUpdateCommand fourthUserForFourthRecipe = new RatingCreateUpdateCommand(10);

    //RatingInfo firstUserForFirstRecipeInfo = new RatingInfo(1, 1, 1, 1);
    //RatingInfo firstUserForSecondRecipeInfo = new RatingInfo(2, 1, 2, 2);
    RatingInfo firstUserForThirdRecipeInfo = new RatingInfo(3, 1, 3, 3);
    //RatingInfo secondUserForFirstRecipeInfo = new RatingInfo(4, 2, 1, 4);
    //RatingInfo secondUserForSecondRecipeInfo = new RatingInfo(5, 2, 2, 5);
    //RatingInfo secondUserForThirdRecipeInfo = new RatingInfo(6, 2, 3, 6);
    RatingInfo thirdUserForFirstRecipeInfo = new RatingInfo(7, 3, 1, 7);
    RatingInfo thirdUserForSecondRecipeInfo = new RatingInfo(8, 3, 2, 8);
    RatingInfo thirdUserForThirdRecipeInfo = new RatingInfo(9, 3, 3, 9);
    RatingInfo fourthUserForFourthRecipeInfo = new RatingInfo(10, 4, 4, 10);

    ValidationError fingersNull = new ValidationError("fingers", "cannot be null");
    ValidationError fingersBelowMin = new ValidationError("fingers", "must be minimum 1");
    ValidationError fingersAboveMax = new ValidationError("fingers", "must be maximum 10");
    ValidationError firstUserForThirdRecipeNotFound = new ValidationError("userId and recipeId",
            "rating by user 1 for recipe 3 is not found");


    @Test
    @Order(1)
    void init() throws Exception {
        //Necessary Users
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(cruellaDeVil1)));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(snowWhite2)));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(yarnFairy3)));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dracula4)));

        //Necessary Recipes
        mockMvc.perform(post("/api/recipes?userId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(firstRecipe)));
        mockMvc.perform(post("/api/recipes?userId=2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondRecipe)));
        mockMvc.perform(post("/api/recipes?userId=3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thirdRecipe)));
        mockMvc.perform(post("/api/recipes?userId=4")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fourthRecipe)));

        //Ratings
        mockMvc.perform(post("/api/users/1/ratings/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(firstUserForFirstRecipe)));
        mockMvc.perform(post("/api/users/1/ratings/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(firstUserForSecondRecipe)));
        mockMvc.perform(post("/api/users/1/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(firstUserForThirdRecipe)));
        mockMvc.perform(post("/api/users/2/ratings/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondUserForFirstRecipe)));
        mockMvc.perform(post("/api/users/2/ratings/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondUserForSecondRecipe)));
        mockMvc.perform(post("/api/users/2/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondUserForThirdRecipe)));
        mockMvc.perform(post("/api/users/3/ratings/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thirdUserForFirstRecipe)));
        mockMvc.perform(post("/api/users/3/ratings/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thirdUserForSecondRecipe)));
        mockMvc.perform(post("/api/users/3/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thirdUserForThirdRecipe)));

    }

    @Test
    @Order(2)
    void testSave_saveRating_201Created() throws Exception {
        // "/{userId}/ratings/{recipeId}"
        mockMvc.perform(post("/api/users/4/ratings/4")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fourthUserForFourthRecipe)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(fourthUserForFourthRecipeInfo)));
    }

    @Test
    @Order(3)
    void testUpdate_updateRating_201Created() throws Exception {
        // "/{userId}/ratings/{recipeId}"
        fourthUserForFourthRecipe.setFingers(8);
        fourthUserForFourthRecipeInfo.setFingers(8);
        mockMvc.perform(post("/api/users/4/ratings/4")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fourthUserForFourthRecipe)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(fourthUserForFourthRecipeInfo)));
    }

    @Test
    @Order(4)
    void testList_findRatingByUserAndRecipe_withThreeUsersWithRecipes() throws Exception {
        // "/{userId}/ratings/{recipeId}"
        mockMvc.perform(get("/api/users/1/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(firstUserForThirdRecipeInfo)));
        mockMvc.perform(get("/api/users/3/ratings/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(thirdUserForSecondRecipeInfo)));
    }

    @Test
    @Order(5)
    void testList_findAllRatingsByUser_200_ok() throws Exception {
        // "/{userId}/ratings/"
        List<RatingInfo> expected = List.of(thirdUserForFirstRecipeInfo, thirdUserForSecondRecipeInfo,
                thirdUserForThirdRecipeInfo);
        mockMvc.perform(get("/api/users/3/ratings/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @Order(6)
    void testDelete_deleteRating_200_ok() throws Exception {
        // "/{userId}/ratings/{recipeId}"
        mockMvc.perform(get("/api/users/2/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(secondUserForThirdRecipe)));
        mockMvc.perform(delete("/api/users/1/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(firstUserForThirdRecipeInfo)));
        mockMvc.perform(get("/api/users/1/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(firstUserForThirdRecipeNotFound))));
    }

    @Test
    @Order(7)
    void testSave_invalidRating_404BadRequest() throws Exception {
        // "/{userId}/ratings/{recipeId}"
        secondUserForThirdRecipe.setFingers(0);
        mockMvc.perform(post("/api/users/2/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondUserForThirdRecipe)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(fingersBelowMin))));

        secondUserForThirdRecipe.setFingers(11);
        mockMvc.perform(post("/api/users/2/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondUserForThirdRecipe)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(fingersAboveMax))));

        secondUserForThirdRecipe.setFingers(null);
        mockMvc.perform(post("/api/users/2/ratings/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondUserForThirdRecipe)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(fingersNull))));
    }

}
