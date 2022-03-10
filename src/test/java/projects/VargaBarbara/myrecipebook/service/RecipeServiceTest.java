package projects.VargaBarbara.myrecipebook.service;

import projects.VargaBarbara.myrecipebook.domain.Recipe;
import projects.VargaBarbara.myrecipebook.domain.User;
import projects.VargaBarbara.myrecipebook.dto.*;
import projects.VargaBarbara.myrecipebook.exceptionhandling.*;
import projects.VargaBarbara.myrecipebook.exceptionhandling.NoAuthorityForActionException;
import projects.VargaBarbara.myrecipebook.exceptionhandling.RecipeNotFoundException;
import projects.VargaBarbara.myrecipebook.exceptionhandling.UserNotFoundException;
import projects.VargaBarbara.myrecipebook.exceptionhandling.ValidationError;
import projects.VargaBarbara.myrecipebook.repository.RatingRepositoryJPA;
import projects.VargaBarbara.myrecipebook.repository.RecipeRepositoryJPA;
import projects.VargaBarbara.myrecipebook.repository.UserRepositoryJPA;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.DirtiesContext;
import projects.VargaBarbara.myrecipebook.dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)       //TODO
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RecipeServiceTest {

    UserRepositoryJPA userRepository = Mockito.mock(UserRepositoryJPA.class);
    RecipeRepositoryJPA recipeRepository = Mockito.mock(RecipeRepositoryJPA.class);
    RatingRepositoryJPA ratingRepository = Mockito.mock(RatingRepositoryJPA.class);
    ModelMapper modelMapper = new ModelMapper();

    //@InjectMocks
    UserService service = new UserService(recipeRepository, ratingRepository, userRepository, modelMapper);

    UserCreateUpdateCommand cruellaDeVil1Command = new UserCreateUpdateCommand
            ("CruellaDeVil1", "cruella.de.vil101@evil.com");
    UserCreateUpdateCommand snowWhite2Command = new UserCreateUpdateCommand
            ("SnowWhite2", "snow.white2@good.com");
    UserCreateUpdateCommand yarnFairy3Command = new UserCreateUpdateCommand
            ("YarnFairy3", "yarn.fairy3@good.com");
    UserCreateUpdateCommand dracula4Command = new UserCreateUpdateCommand
            ("Dracula4", "dracula4@whatever.com");

    UserInfo cruellaDeVil1Info = new UserInfo(1, "CruellaDeVil1", "cruella.de.vil101@evil.com", List.of());
    UserInfo snowWhite2Info = new UserInfo(2, "SnowWhite2", "snow.white2@good.com", List.of());
    UserInfo yarnFairy3Info = new UserInfo(3, "YarnFairy3", "yarn.fairy3@good.com", List.of());
    UserInfo dracula4Info = new UserInfo(4, "Dracula4", "dracula4@whatever.com", List.of());


    User cruellaDeVil1 = new User(null, "CruellaDeVil1", "cruella.de.vil101@evil.com", List.of());
    User snowWhite2 = new User(null, "SnowWhite2", "snow.white2@good.com", List.of());
    User yarnFairy3 = new User(null, "YarnFairy3", "yarn.fairy3@good.com", List.of());
    User dracula4 = new User(null, "Dracula4", "dracula4@whatever.com", List.of());

    User cruellaDeVil1FromRepo = new User(1, "CruellaDeVil1", "cruella.de.vil101@evil.com", List.of());
    User snowWhite2FromRepo = new User(2, "SnowWhite2", "snow.white2@good.com", List.of());
    User yarnFairy3FromRepo = new User(3, "YarnFairy3", "yarn.fairy3@good.com", List.of());
    User dracula4FromRepo = new User(4, "Dracula4", "dracula4@whatever.com", List.of());


    //TODO FromRepo
    String beautyBeast1Preparation = "Tüzes borral teli pohár ontja bíbor illatát. " +
            "Végül még teázni sikk, édességhez jólesik.";
    String beautyBeast1Note = "Szörnyen jó, bon appetit!";
    String breadedDrawer2Preparation = "Kirántott fiók recept! " +
            "Végy egy jókora fiókot és könnyed laza mozdulattal rántsd ki.";
    String breadedDrawer2Note = "Omlósabb lesz, ha szúval előfűszerezzük.";
    String hunterGames3Preparation = "Vadászvacsora! Vadászd össze a kamra tartalmát, " +
            "ízlésesen helyezd egy tálcára és fogyaszd egészséggel.";
    ;
    String hunterGames3Note = "Csak ehető dolgokra vadássz!";
    String shakeTheMilk4Preparation = "Milkshake 2 személyre! Önts 4 dl tejet egy shakerbe, " +
            "majd 15 percig lendületes mozdulatokkal rázd össze.";
    String shakeTheMilk4Note = "Riszálom úgyis-úgyis!";

    LocalDate creationDate = LocalDate.now(); //ForAllRecipes
    LocalDate lastEditDate = LocalDate.now(); //ForAllRecipes

    RecipeCreateUpdateCommand firstRecipeCommand = new RecipeCreateUpdateCommand(beautyBeast1Preparation, beautyBeast1Note);
    RecipeCreateUpdateCommand secondRecipeCommand = new RecipeCreateUpdateCommand(breadedDrawer2Preparation, breadedDrawer2Note);
    RecipeCreateUpdateCommand thirdRecipeCommand = new RecipeCreateUpdateCommand(hunterGames3Preparation, hunterGames3Note);
    RecipeCreateUpdateCommand fourthRecipeCommand = new RecipeCreateUpdateCommand(shakeTheMilk4Preparation, shakeTheMilk4Note);

    RecipeInfo firstRecipeInfo = new RecipeInfo(1, beautyBeast1Preparation, beautyBeast1Note,
            1, creationDate, lastEditDate, 0.0);
    RecipeInfo secondRecipeInfo = new RecipeInfo(2, breadedDrawer2Preparation, breadedDrawer2Note,
            2, creationDate, lastEditDate, 0.0);
    RecipeInfo thirdRecipeInfo = new RecipeInfo(3, hunterGames3Preparation, hunterGames3Note,
            3, creationDate, lastEditDate, 0.0);
    RecipeInfo fourthRecipeInfo = new RecipeInfo(4, shakeTheMilk4Preparation, shakeTheMilk4Note,
            4, creationDate, lastEditDate, 0.0);

    Recipe firstRecipe = new Recipe(null, beautyBeast1Preparation, beautyBeast1Note,
            cruellaDeVil1FromRepo, creationDate, lastEditDate);
    Recipe secondRecipe = new Recipe(null, breadedDrawer2Preparation, breadedDrawer2Note,
            snowWhite2FromRepo, creationDate, lastEditDate);
    Recipe thirdRecipe = new Recipe(null, hunterGames3Preparation, hunterGames3Note,
            yarnFairy3FromRepo, creationDate, lastEditDate);
    Recipe fourthRecipe = new Recipe(null, shakeTheMilk4Preparation, shakeTheMilk4Note,
            dracula4FromRepo, creationDate, lastEditDate);

    Recipe firstRecipeFromRepo = new Recipe(1, beautyBeast1Preparation, beautyBeast1Note,
            cruellaDeVil1FromRepo, creationDate, lastEditDate);
    Recipe secondRecipeFromRepo = new Recipe(2, breadedDrawer2Preparation, breadedDrawer2Note,
            snowWhite2FromRepo, creationDate, lastEditDate);
    Recipe thirdRecipeFromRepo = new Recipe(3, hunterGames3Preparation, hunterGames3Note,
            yarnFairy3FromRepo, creationDate, lastEditDate);
    Recipe fourthRecipeFromRepo = new Recipe(4, shakeTheMilk4Preparation, shakeTheMilk4Note,
            dracula4FromRepo, creationDate, lastEditDate);

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

    RatingInfo firstUserForFirstRecipeInfo = new RatingInfo(1, 1, 1, 1);
    RatingInfo firstUserForSecondRecipeInfo = new RatingInfo(2, 1, 2, 2);
    RatingInfo firstUserForThirdRecipeInfo = new RatingInfo(3, 1, 3, 3);
    RatingInfo secondUserForFirstRecipeInfo = new RatingInfo(4, 2, 1, 4);
    RatingInfo secondUserForSecondRecipeInfo = new RatingInfo(5, 2, 2, 5);
    RatingInfo secondUserForThirdRecipeInfo = new RatingInfo(6, 2, 3, 6);
    RatingInfo thirdUserForFirstRecipeInfo = new RatingInfo(7, 3, 1, 7);
    RatingInfo thirdUserForSecondRecipeInfo = new RatingInfo(8, 3, 2, 8);
    RatingInfo thirdUserForThirdRecipeInfo = new RatingInfo(9, 3, 3, 9);
    RatingInfo fourthUserForFourthRecipeInfo = new RatingInfo(10, 4, 4, 10);


    //TODO feleslegesváltozók törlése

    ValidationError nameBlank = new ValidationError("name", "cannot be blank");
    ValidationError emailBlank = new ValidationError("email", "cannot be blank");
    ValidationError emailFormat = new ValidationError("email", "format must be email");

    ValidationError cruellaNotFound = new ValidationError("userID", "user with id 1 is not found");
    ValidationError snowNotFound = new ValidationError("userID", "user with id 2 is not found");
    ValidationError firstRecipeNotFound = new ValidationError("recipeId", "recipe with id 1 is not found");
    ValidationError secondRecipeNotFound = new ValidationError("recipeId", "recipe with id 2 is not found");
    ValidationError fourthRecipeNotFound = new ValidationError("recipeId", "recipe with id 4 is not found");

    ValidationError preparationBlank = new ValidationError("preparation", "cannot be blank");
    ValidationError noteTooLong = new ValidationError("note", "maximum length 100");

    ValidationError fingersNull = new ValidationError("fingers", "cannot be null");
    ValidationError fingersBelowMin = new ValidationError("fingers", "must be minimum 1");
    ValidationError fingersAboveMax = new ValidationError("fingers", "must be maximum 10");
    ValidationError firstUserForThirdRecipeNotFound = new ValidationError("userId and recipeId",
            "rating by user 1 for recipe 3 is not found");

    @Test
    void saveRecipe() {
        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        when(recipeRepository.save(firstRecipe)).thenReturn(Optional.of(firstRecipeFromRepo));
        assertEquals(firstRecipeInfo, service.saveRecipe(1, creationDate, firstRecipeCommand));

        when(userRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.saveRecipe(2, creationDate, secondRecipeCommand));

        verify(userRepository, times(2)).findById(any());
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    void findAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(List.of(firstRecipeFromRepo, secondRecipeFromRepo));
        assertEquals(List.of(firstRecipeInfo, secondRecipeInfo), service.findAllRecipes());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void findRecipeById() {
        //Found case
        when(recipeRepository.findById(1)).thenReturn(Optional.of(firstRecipeFromRepo));
        assertEquals(firstRecipeInfo, service.findRecipeById(1));

        //RecipeNotFound case
        when(recipeRepository.findById(3)).thenReturn(Optional.empty());
        assertThrows(RecipeNotFoundException.class, () -> service.findRecipeById(3));
        verify(recipeRepository, times(2)).findById(any());
    }

    @Test
    void findRecipesByUser() {
        //User found case
        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        when(recipeRepository.findByUser(1)).thenReturn(List.of(firstRecipeFromRepo));
        assertEquals(List.of(firstRecipeInfo), service.findRecipesByUser(1));
        verify(recipeRepository, times(1)).findByUser(any());

        //UserNotFound case
        when(recipeRepository.findByUser(3)).thenReturn(List.of());
        assertThrows(UserNotFoundException.class, () -> service.findRecipesByUser(3));
        verify(userRepository, times(2)).findById(any());
    }

    @Test
    void updateRecipe() {
        when(recipeRepository.findById(2)).thenReturn(Optional.of(secondRecipeFromRepo));
        String newNote = "A szú mégsem volt olyan jó ötlet";
        secondRecipeFromRepo.setNote(newNote);
        secondRecipeCommand.setNote(newNote);
        secondRecipeInfo.setNote(newNote);
        when(recipeRepository.update(secondRecipeFromRepo)).thenReturn(Optional.of(secondRecipeFromRepo));
        assertEquals(secondRecipeInfo, service.updateRecipe(2, 2, lastEditDate, secondRecipeCommand));

        //RecipeNotFound case
        when(recipeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RecipeNotFoundException.class,
                () -> service.updateRecipe(1, 1, lastEditDate, secondRecipeCommand));

        //NoAuthorityForAction case
        assertThrows(NoAuthorityForActionException.class,
                () -> service.updateRecipe(2, 3, lastEditDate, secondRecipeCommand));

        verify(recipeRepository, times(3)).findById(any());
        verify(recipeRepository, times(1)).update(any());
    }

    @Test
    void deleteRecipe() {
        when(recipeRepository.findById(1)).thenReturn(Optional.of(firstRecipeFromRepo));
        when(recipeRepository.delete(firstRecipeFromRepo)).thenReturn(Optional.of(firstRecipeFromRepo));
        assertEquals(firstRecipeInfo, service.deleteRecipe(1, 1));

        //RecipeNotFound case
        when(recipeRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(RecipeNotFoundException.class, () -> service.deleteRecipe(2, 2));

        //NoAuthorityForAction case
        when(recipeRepository.findById(2)).thenReturn(Optional.of(secondRecipeFromRepo));
        assertThrows(NoAuthorityForActionException.class,
                () -> service.deleteRecipe(2, 3));

        verify(recipeRepository, times(3)).findById(any());
        verify(recipeRepository, times(1)).delete(any());
    }

    /*@Test
    void saveOrUpdateRating() {
    }

    @Test
    void saveRating() {
    }

    @Test
    void findRatingByUserAndRecipe() {
    }

    @Test
    void findAllRatingsByUser() {
    }

    @Test
    void updateRating() {
    }

    @Test
    void deleteRating() {
    }*/
}
