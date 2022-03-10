package projects.VargaBarbara.myrecipebook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import projects.VargaBarbara.myrecipebook.dto.*;
import projects.VargaBarbara.myrecipebook.exceptionhandling.ValidationError;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import projects.VargaBarbara.myrecipebook.dto.RecipeCreateUpdateCommand;
import projects.VargaBarbara.myrecipebook.dto.RecipeInfo;
import projects.VargaBarbara.myrecipebook.dto.UserCreateUpdateCommand;
import projects.VargaBarbara.myrecipebook.dto.UserInfo;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerTest {

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

    UserInfo cruellaDeVil1Info = new UserInfo(1, "CruellaDeVil1", "cruella.de.vil101@evil.com", List.of());
    UserInfo snowWhite2Info = new UserInfo(2, "SnowWhite2", "snow.white2@good.com", List.of());
    UserInfo yarnFairy3Info = new UserInfo(3, "YarnFairy3", "yarn.fairy3@good.com", List.of());
    //UserInfo dracula4Info = new UserInfo(4, "Dracula4", "dracula4@whatever.com", List.of());

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
            1, creationDate, lastEditDate, 0.0);
    RecipeInfo thirdRecipeInfo = new RecipeInfo(3, hunterGames3Preparation, hunterGames3Note,
            2, creationDate, lastEditDate, 0.0);
    RecipeInfo fourthRecipeInfo = new RecipeInfo(4, shakeTheMilk4Preparation, shakeTheMilk4Note,
            2, creationDate, lastEditDate, 0.0);

    ValidationError nameBlank = new ValidationError("name", "cannot be blank");
    ValidationError emailBlank = new ValidationError("email", "cannot be blank");
    ValidationError emailFormat = new ValidationError("email", "format must be email");

    ValidationError cruellaNotFound = new ValidationError("userID", "user with id 1 is not found");
    ValidationError snowNotFound = new ValidationError("userID", "user with id 2 is not found");
    ValidationError firstRecipeNotFound = new ValidationError("recipeId", "recipe with id 1 is not found");
    ValidationError secondRecipeNotFound = new ValidationError("recipeId", "recipe with id 2 is not found");

    @Test
    @Order(1)
    void init() throws Exception {

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(cruellaDeVil1)));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(snowWhite2)));
    }

    @Test
    @Order(2)
    void testSave_validUser_201Created() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(yarnFairy3)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("YarnFairy3")))
                .andExpect(jsonPath("$.email", is("yarn.fairy3@good.com")))
                .andExpect(jsonPath("$.recipes", is(List.of())));
    }

    @Test
    @Order(3)
    void testSave_everythingWrong_400BadRequestAndAllErrorMessages() throws Exception {
        List<ValidationError> validationErrors = List.of(nameBlank, emailBlank, emailFormat);
        dracula4.setName("     ");
        dracula4.setEmail("       ");
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dracula4)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(validationErrors)));
    }

    @Test
    @Order(4)
    void testList_findAllUsers_withThreeUsersNoRecipes() throws Exception {
        List<UserInfo> userInfos = List.of(cruellaDeVil1Info, snowWhite2Info, yarnFairy3Info);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userInfos)));
    }

    @Test
    @Order(5)
    void testList_findAllUsers_withThreeUsersWithRecipes() throws Exception {
        cruellaDeVil1Info.setRecipes(List.of(firstRecipeInfo, secondRecipeInfo));
        snowWhite2Info.setRecipes(List.of(thirdRecipeInfo, fourthRecipeInfo));
        List<UserInfo> expected = List.of(
                cruellaDeVil1Info,
                snowWhite2Info,
                yarnFairy3Info
        );
        mockMvc.perform(post("/api/recipes?userId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(firstRecipe)));
        mockMvc.perform(post("/api/recipes?userId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(secondRecipe)));
        mockMvc.perform(post("/api/recipes?userId=2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(thirdRecipe)));
        mockMvc.perform(post("/api/recipes?userId=2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fourthRecipe)));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @Order(6)
    void testList_findUserById_withRecipes() throws Exception {
        // "/{id}"
        snowWhite2Info.setRecipes(List.of(thirdRecipeInfo, fourthRecipeInfo));
        UserInfo expected = snowWhite2Info;
        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @Order(7)
    void testList_updateUser_oneUserWithRecipes() throws Exception {
        // "/{toUpdateId}"
        String newEmail = "cruella.de.vil1@evil.com";
        cruellaDeVil1.setEmail(newEmail);
        cruellaDeVil1Info.setEmail(newEmail);
        cruellaDeVil1Info.setRecipes(List.of(firstRecipeInfo, secondRecipeInfo));
        mockMvc.perform(put("/api/users/1?loggedInUserId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(cruellaDeVil1)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(cruellaDeVil1Info)));
    }

    @Test
    @Order(8)
    void testList_deleteUser_oneUserWithRecipes() throws Exception {
        // "/{toDeleteId}"
        cruellaDeVil1Info.setEmail("cruella.de.vil1@evil.com");
        cruellaDeVil1Info.setRecipes(List.of(firstRecipeInfo, secondRecipeInfo));

        mockMvc.perform(delete("/api/users/1?loggedInUserId=1&deleteRecipes"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cruellaDeVil1Info)));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(cruellaNotFound))));

        mockMvc.perform(get("/api/recipes/recipe/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(firstRecipeNotFound))));
        mockMvc.perform(get("/api/recipes/recipe/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(secondRecipeNotFound))));
    }

    @Test
    @Order(9)
    void testList_deleteUser_oneUserWithoutRecipes() throws Exception {
        // "/{toDeleteId}"
        snowWhite2Info.setRecipes(List.of());
        thirdRecipeInfo.setCreatorId(0);
        fourthRecipeInfo.setCreatorId(0);

        mockMvc.perform(delete("/api/users/2?loggedInUserId=2&deleteRecipes=false"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(snowWhite2Info)));

        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(snowNotFound))));

        mockMvc.perform(get("/api/recipes/recipe/3"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(thirdRecipeInfo)));
        mockMvc.perform(get("/api/recipes/recipe/4"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fourthRecipeInfo)));
    }

}
