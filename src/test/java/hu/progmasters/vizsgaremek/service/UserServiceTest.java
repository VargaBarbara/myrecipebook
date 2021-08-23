package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Recipe;
import hu.progmasters.vizsgaremek.domain.User;
import hu.progmasters.vizsgaremek.dto.*;
import hu.progmasters.vizsgaremek.exceptionhandling.*;
import hu.progmasters.vizsgaremek.repository.RatingRepositoryJPA;
import hu.progmasters.vizsgaremek.repository.RecipeRepositoryJPA;
import hu.progmasters.vizsgaremek.repository.UserRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserRepositoryJPA userRepository = Mockito.mock(UserRepositoryJPA.class);
    RecipeRepositoryJPA recipeRepository = Mockito.mock(RecipeRepositoryJPA.class);
    RatingRepositoryJPA ratingRepository = Mockito.mock(RatingRepositoryJPA.class);
    ModelMapper modelMapper = new ModelMapper();

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

    @Test
    void saveUser() {
        when(userRepository.findByEmail(cruellaDeVil1Command.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(cruellaDeVil1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));

        assertEquals(cruellaDeVil1Info, service.saveUser(cruellaDeVil1Command));

        //EmailIsAlreadyInUse case
        when(userRepository.findByEmail(snowWhite2Command.getEmail())).thenReturn(Optional.of(snowWhite2FromRepo));
        assertThrows(EmailIsAlreadyInUseException.class, () -> service.saveUser(snowWhite2Command));

        verify(userRepository, times(2)).findByEmail(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void findAllUsers() {
        when(userRepository.findAll())
                .thenReturn(List.of(cruellaDeVil1FromRepo, snowWhite2FromRepo, yarnFairy3FromRepo, dracula4FromRepo));
        List<UserInfo> expectedInfos = List.of(cruellaDeVil1Info, snowWhite2Info, yarnFairy3Info, dracula4Info);
        assertEquals(expectedInfos, service.findAllUsers());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAllUsersWithRecipes() {
        cruellaDeVil1FromRepo.setRecipes(List.of(firstRecipeFromRepo));
        snowWhite2FromRepo.setRecipes(List.of(secondRecipeFromRepo));
        yarnFairy3FromRepo.setRecipes(List.of(thirdRecipeFromRepo));
        dracula4FromRepo.setRecipes(List.of(fourthRecipeFromRepo));
        when(userRepository.findAll())
                .thenReturn(List.of(cruellaDeVil1FromRepo, snowWhite2FromRepo, yarnFairy3FromRepo, dracula4FromRepo));

        cruellaDeVil1Info.setRecipes(List.of(firstRecipeInfo));
        snowWhite2Info.setRecipes(List.of(secondRecipeInfo));
        yarnFairy3Info.setRecipes(List.of(thirdRecipeInfo));
        dracula4Info.setRecipes(List.of(fourthRecipeInfo));
        List<UserInfo> expectedInfos = List.of(cruellaDeVil1Info, snowWhite2Info, yarnFairy3Info, dracula4Info);
        assertEquals(expectedInfos, service.findAllUsers());

        verify(userRepository, times(1)).findAll();

    }

    @Test
    void findUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        assertEquals(cruellaDeVil1Info, service.findUserById(1));

        when(userRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.findUserById(2));

        verify(userRepository, times(2)).findById(any());
    }

    @Test
    void updateUser() {
        String newEmail = "cruella.de.vil101@evil.com";
        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        cruellaDeVil1FromRepo.setEmail(newEmail);
        cruellaDeVil1Command.setEmail(newEmail);
        cruellaDeVil1Info.setEmail(newEmail);
        when(userRepository.findByEmail(cruellaDeVil1FromRepo.getEmail())).thenReturn(Optional.empty());

        //Updated case
        when(userRepository.update(cruellaDeVil1FromRepo)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        assertEquals(cruellaDeVil1Info, service.updateUser(1, 1, cruellaDeVil1Command));

        //UserNotFound case
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> service.updateUser(2, 2, snowWhite2Command));

        //NoAuthorityForAction case
        assertThrows(NoAuthorityForActionException.class,
                () -> service.updateUser(1, 2, cruellaDeVil1Command));

        //EmailIsAlreadyInUse case
        when(userRepository.findByEmail(cruellaDeVil1FromRepo.getEmail())).thenReturn(Optional.of(snowWhite2FromRepo));
        assertThrows(EmailIsAlreadyInUseException.class,
                () -> service.updateUser(1, 1, cruellaDeVil1Command));
    }

    @Test
    void deleteUser() { //TODO simplifying
        //saves
        when(userRepository.findByEmail(cruellaDeVil1Command.getEmail())).thenReturn(Optional.empty());//
        when(userRepository.findByEmail(snowWhite2Command.getEmail())).thenReturn(Optional.empty());//
        when(userRepository.save(cruellaDeVil1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));//
        when(userRepository.save(snowWhite2)).thenReturn(Optional.of(snowWhite2FromRepo));//

        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));//
        when(userRepository.findById(2)).thenReturn(Optional.of(snowWhite2FromRepo));//
        when(recipeRepository.save(firstRecipe)).thenReturn(Optional.of(firstRecipeFromRepo));//
        when(recipeRepository.save(secondRecipe)).thenReturn(Optional.of(secondRecipeFromRepo));//

        service.saveUser(cruellaDeVil1Command);
        service.saveUser(snowWhite2Command);
        RecipeInfo firstRecipeInfoSaved = service.saveRecipe(1, creationDate, firstRecipeCommand);
        RecipeInfo secondRecipeInfoSaved = service.saveRecipe(2, creationDate, secondRecipeCommand);

        //adding recipes to users
        cruellaDeVil1FromRepo.setRecipes(List.of(firstRecipeFromRepo));
        snowWhite2FromRepo.setRecipes(List.of(secondRecipeFromRepo));
        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));//
        when(userRepository.findById(2)).thenReturn(Optional.of(snowWhite2FromRepo));//

        //UserNotFound case
        assertThrows(UserNotFoundException.class, () -> service.deleteUser(3, 3, false));

        //NoAuthorityForAction case
        assertThrows(NoAuthorityForActionException.class,
                () -> service.deleteUser(1, 2, false));

        //deleteUserWithRecipes case
        when(recipeRepository.delete(firstRecipeFromRepo)).thenReturn(Optional.of(firstRecipeFromRepo));//
        cruellaDeVil1FromRepo.setRecipes(List.of());
        when(userRepository.delete(cruellaDeVil1FromRepo)).thenReturn(Optional.of(cruellaDeVil1FromRepo));

        UserInfo deletedCruellaInfo = service.deleteUser(1, 1, true);

        when(userRepository.findById(1)).thenReturn(Optional.empty());//
        when(recipeRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.findUserById(deletedCruellaInfo.getId()));
        assertThrows(RecipeNotFoundException.class, () -> service.findRecipeById(firstRecipeFromRepo.getId()));

        //deleteUserWithoutRecipes case
        snowWhite2FromRepo.setRecipes(List.of());
        when(userRepository.delete(snowWhite2FromRepo)).thenReturn(Optional.of(snowWhite2FromRepo));

        UserInfo deletedSnowInfo = service.deleteUser(2, 2, false);
        when(userRepository.findById(2)).thenReturn(Optional.empty());//
        snowWhite2Info.setRecipes(List.of());
        assertEquals(snowWhite2Info, deletedSnowInfo);
        assertThrows(UserNotFoundException.class, () -> service.findUserById(deletedSnowInfo.getId()));

        secondRecipeFromRepo.setCreator(null);
        secondRecipeInfoSaved.setCreatorId(0);
        when(recipeRepository.findById(2)).thenReturn(Optional.of(secondRecipeFromRepo));
        assertEquals(secondRecipeInfoSaved, service.findRecipeById(secondRecipeInfoSaved.getId()));

        verify(userRepository, times(2)).findByEmail(any());
        verify(userRepository, times(2)).save(any());
        verify(userRepository, times(8)).findById(any());
        verify(userRepository, times(2)).delete(any());
        verify(recipeRepository, times(2)).save(any());
        verify(recipeRepository, times(2)).findById(any());
    }
}