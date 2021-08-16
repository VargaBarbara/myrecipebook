package hu.progmasters.vizsgaremek.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import hu.progmasters.vizsgaremek.dto.RecipeCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.RecipeInfo;
import hu.progmasters.vizsgaremek.dto.UserCreateUpdateCommand;
import hu.progmasters.vizsgaremek.exceptionhandling.ValidationError;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RecipeControllerTest {

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

    LocalDate creationDate = LocalDate.now(); //ForAllRecipes
    LocalDate lastEditDate = LocalDate.now(); //ForAllRecipes

    RecipeCreateUpdateCommand firstRecipe = new RecipeCreateUpdateCommand(beautyBeast1Preparation, beautyBeast1Note);
    RecipeCreateUpdateCommand secondRecipe = new RecipeCreateUpdateCommand(breadedDrawer2Preparation, breadedDrawer2Note);
    RecipeCreateUpdateCommand thirdRecipe = new RecipeCreateUpdateCommand(hunterGames3Preparation, hunterGames3Note);
    RecipeCreateUpdateCommand fourthRecipe = new RecipeCreateUpdateCommand(shakeTheMilk4Preparation, shakeTheMilk4Note);

    RecipeInfo firstRecipeInfo = new RecipeInfo(1, beautyBeast1Preparation, beautyBeast1Note,
            1, creationDate, lastEditDate, 0.0);
    RecipeInfo secondRecipeInfo = new RecipeInfo(2, breadedDrawer2Preparation, breadedDrawer2Note,
            2, creationDate, lastEditDate, 0.0);
    RecipeInfo thirdRecipeInfo = new RecipeInfo(3, hunterGames3Preparation, hunterGames3Note,
            3, creationDate, lastEditDate, 0.0);
    RecipeInfo fourthRecipeInfo = new RecipeInfo(4, shakeTheMilk4Preparation, shakeTheMilk4Note,
            4, creationDate, lastEditDate, 0.0);

    ValidationError fourthRecipeNotFound = new ValidationError("recipeId", "recipe with id 4 is not found");

    ValidationError preparationBlank = new ValidationError("preparation", "cannot be blank");
    ValidationError noteTooLong = new ValidationError("note", "maximum length 100");

    //"/api/recipes"

    @Test
    @Order(1)
    public void init() throws Exception {

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

        //Recipes
        mockMvc.perform(post("/api/recipes?userId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(firstRecipe)));
        mockMvc.perform(post("/api/recipes?userId=2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondRecipe)));
    }

    @Test
    @Order(2)
    void testSave_validRecipe_201Created() throws Exception {
        //@RequestParam Integer userId
        mockMvc.perform(post("/api/recipes?userId=3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thirdRecipe)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(thirdRecipeInfo)));
    }

    @Test
    @Order(3)
    void testSave_everythingWrong_400BadRequest() throws Exception {
        fourthRecipe.setPreparation("    ");
        fourthRecipe.setNote("01234567890123456789012345678901234567890123456789012345678901234567890123456789" +
                "012345678901234567890123456789");
        List<ValidationError> validationErrors = List.of(preparationBlank, noteTooLong);

        mockMvc.perform(post("/api/recipes?userId=4")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fourthRecipe)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(validationErrors)));
    }

    @Test
    @Order(4)
    void testList_findAllRecipes_200_Ok() throws Exception {
        List<RecipeInfo> expected = List.of(firstRecipeInfo, secondRecipeInfo, thirdRecipeInfo);
        mockMvc.perform(get("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @Order(5)
    void testList_findRecipeById_200_Ok() throws Exception {
        //"/recipe/{recipeId}"
        mockMvc.perform(get("/api/recipes/recipe/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(thirdRecipeInfo)));
    }

    @Test
    @Order(6)
    void testList_findRecipesByUser_200_Ok() throws Exception {
        //"/user/{userId}"
        //savefourthRecipeFor User 1
        fourthRecipeInfo.setCreatorId(1);

        mockMvc.perform(post("/api/recipes?userId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fourthRecipe)));

        List<RecipeInfo> expected = List.of(firstRecipeInfo, fourthRecipeInfo);
        mockMvc.perform(get("/api/recipes/user/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @Order(7)
    void testList_updateRecipe_201Created() throws Exception {
        //@RequestParam Integer userId
        //"/{recipeId}"
        String newNoteFor2 = "A szú nem volt valami jó ötlet.";
        secondRecipe.setNote(newNoteFor2);
        secondRecipeInfo.setNote(newNoteFor2);

        mockMvc.perform(put("/api/recipes/2?userId=2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondRecipe)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(secondRecipeInfo)));
    }

    @Test
    @Order(8)
    void testList_deleteRecipe__200_Ok() throws Exception {
        //@RequestParam Integer userId
        //"/{recipeId}"
        fourthRecipeInfo.setCreatorId(1);
        mockMvc.perform(get("/api/recipes/recipe/4")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fourthRecipeInfo)));

        mockMvc.perform(delete("/api/recipes/4?userId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fourthRecipeInfo)));

        mockMvc.perform(get("/api/recipes/recipe/4")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(fourthRecipeNotFound))));
    }

}
