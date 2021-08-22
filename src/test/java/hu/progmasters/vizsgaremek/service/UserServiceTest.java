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

    LocalDate creationDate = LocalDate.now(); //ForAllRecipes
    LocalDate lastEditDate = LocalDate.now(); //ForAllRecipes

    RecipeCreateUpdateCommand firstRecipeCommand = new RecipeCreateUpdateCommand(beautyBeast1Preparation, beautyBeast1Note);
    RecipeCreateUpdateCommand secondRecipeCommand = new RecipeCreateUpdateCommand(breadedDrawer2Preparation, breadedDrawer2Note);

    Recipe firstRecipe = new Recipe(null, beautyBeast1Preparation, beautyBeast1Note,
            cruellaDeVil1FromRepo, creationDate, lastEditDate);
    Recipe secondRecipe = new Recipe(null, breadedDrawer2Preparation, breadedDrawer2Note,
            snowWhite2FromRepo, creationDate, lastEditDate);

    Recipe firstRecipeFromRepo = new Recipe(1, beautyBeast1Preparation, beautyBeast1Note,
            cruellaDeVil1FromRepo, creationDate, lastEditDate);
    Recipe secondRecipeFromRepo = new Recipe(2, breadedDrawer2Preparation, breadedDrawer2Note,
            snowWhite2FromRepo, creationDate, lastEditDate);

    @Test
    void saveUser() {
        when(userRepository.findByEmail(cruellaDeVil1.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(cruellaDeVil1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        UserInfo cruellaTestInfo = service.saveUser(cruellaDeVil1Command);
        assertEquals(cruellaDeVil1Info, cruellaTestInfo);

        when(userRepository.findByEmail(cruellaDeVil1.getEmail())).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        assertThrows(EmailIsAlreadyInUseException.class, () -> service.saveUser(cruellaDeVil1Command));

        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(2)).findByEmail(any());
    }

    @Test
    void findAllUsers() {
        when(userRepository.save(cruellaDeVil1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        when(userRepository.save(snowWhite2)).thenReturn(Optional.of(snowWhite2FromRepo));
        when(userRepository.save(yarnFairy3)).thenReturn(Optional.of(yarnFairy3FromRepo));
        when(userRepository.save(dracula4)).thenReturn(Optional.of(dracula4FromRepo));
        when(userRepository.findAll()).thenReturn(List.of(cruellaDeVil1FromRepo, snowWhite2FromRepo,
                yarnFairy3FromRepo, dracula4FromRepo));
        service.saveUser(cruellaDeVil1Command);
        service.saveUser(snowWhite2Command);
        service.saveUser(yarnFairy3Command);
        service.saveUser(dracula4Command);

        List<UserInfo> actualUsers = service.findAllUsers();
        assertEquals(List.of(cruellaDeVil1Info, snowWhite2Info, yarnFairy3Info, dracula4Info), actualUsers);

        verify(userRepository, times(4)).save(any());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAllUsersWithRecipes() {
        when(userRepository.findByEmail(cruellaDeVil1Command.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(snowWhite2Command.getEmail())).thenReturn(Optional.empty());

        when(userRepository.save(cruellaDeVil1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        when(userRepository.save(snowWhite2)).thenReturn(Optional.of(snowWhite2FromRepo));

        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        when(userRepository.findById(2)).thenReturn(Optional.of(snowWhite2FromRepo));

        when(recipeRepository.save(firstRecipe)).thenReturn(Optional.of(firstRecipeFromRepo));
        when(recipeRepository.save(secondRecipe)).thenReturn(Optional.of(secondRecipeFromRepo));

        cruellaDeVil1FromRepo.setRecipes(List.of(firstRecipeFromRepo));
        snowWhite2FromRepo.setRecipes(List.of(secondRecipeFromRepo));
        when(userRepository.findAll()).thenReturn(List.of(cruellaDeVil1FromRepo, snowWhite2FromRepo));

        UserInfo userInfo1 = service.saveUser(cruellaDeVil1Command);
        UserInfo userInfo2 = service.saveUser(snowWhite2Command);
        RecipeInfo recipeInfo1 = service.saveRecipe(1, creationDate, firstRecipeCommand);
        RecipeInfo recipeInfo2 = service.saveRecipe(2, creationDate, secondRecipeCommand);

        userInfo1.setRecipes(List.of(recipeInfo1));
        userInfo2.setRecipes(List.of(recipeInfo2));
        List<UserInfo> expectedUserInfos = List.of(userInfo1, userInfo2);

        List<UserInfo> actualUserInfos = service.findAllUsers();
        assertEquals(expectedUserInfos, actualUserInfos);

        verify(userRepository, times(2)).findByEmail(any());
        verify(userRepository, times(2)).save(any());
        verify(userRepository, times(2)).findById(any());
        verify(recipeRepository, times(2)).save(any());
    }

    @Test
    void findUserById() {
        when(userRepository.findByEmail(cruellaDeVil1Command.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(cruellaDeVil1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        UserInfo savedInfo = service.saveUser(cruellaDeVil1Command);
        UserInfo foundById1 = service.findUserById(1);
        assertEquals(savedInfo, foundById1);
        assertThrows(UserNotFoundException.class, () -> service.findUserById(2));

        verify(userRepository, times(1)).findByEmail(any());
        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(2)).findById(any());
    }

    @Test
    void updateUser() {
        when(userRepository.findByEmail(cruellaDeVil1Command.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(cruellaDeVil1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        service.saveUser(cruellaDeVil1Command);

        //Updated case
        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        cruellaDeVil1FromRepo.setEmail("cruella.de.vil1@evil.com");
        when(userRepository.findByEmail(cruellaDeVil1FromRepo.getEmail())).thenReturn(Optional.empty());
        when(userRepository.update(cruellaDeVil1FromRepo)).thenReturn(Optional.of(cruellaDeVil1FromRepo));

        cruellaDeVil1Command.setEmail("cruella.de.vil1@evil.com");
        UserInfo cruellaInfoAfterUpdate = service.updateUser(1, 1, cruellaDeVil1Command);
        assertEquals("cruella.de.vil1@evil.com", cruellaInfoAfterUpdate.getEmail());

        //UserNotFound case
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(snowWhite2Command.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> service.updateUser(2, 2, snowWhite2Command));

        //NoAuthorityForAction case
        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        when(userRepository.findByEmail(cruellaDeVil1FromRepo.getEmail())).thenReturn(Optional.of(cruellaDeVil1FromRepo));

        assertThrows(NoAuthorityForActionException.class,
                () -> service.updateUser(1, 2, cruellaDeVil1Command));

        //EmailIsAlreadyInUse case
        when(userRepository.findByEmail(snowWhite2.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(snowWhite2)).thenReturn(Optional.of(snowWhite2FromRepo));
        service.saveUser(snowWhite2Command);

        when(userRepository.findById(1)).thenReturn(Optional.of(cruellaDeVil1FromRepo));
        cruellaDeVil1FromRepo.setEmail(snowWhite2FromRepo.getEmail());
        when(userRepository.findByEmail(snowWhite2Command.getEmail())).thenReturn(Optional.of(snowWhite2FromRepo));

        cruellaDeVil1Command.setEmail(snowWhite2Command.getEmail());
        assertThrows(EmailIsAlreadyInUseException.class,
                () -> service.updateUser(1, 1, cruellaDeVil1Command));

        verify(userRepository, times(6)).findByEmail(any());
        verify(userRepository, times(2)).save(any());
        verify(userRepository, times(4)).findById(any());
        verify(userRepository, times(1)).update(any());
    }

    @Test
    void deleteUser() {
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