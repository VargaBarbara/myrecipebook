package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Rating;
import hu.progmasters.vizsgaremek.domain.Recipe;
import hu.progmasters.vizsgaremek.domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
class RatingRepositoryJPATest {

    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    @Autowired
    RecipeRepositoryJPA recipeRepositoryJPA;

    @Autowired
    RatingRepositoryJPA ratingRepositoryJPA;

    User cruellaDeVil1;
    User snowWhite2;

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

    Recipe beautyBeast1;
    Recipe breadedDrawer2;
    Recipe hunterGames3;
    Recipe shakeTheMilk4;



    @Test
    @Order(1)
    @Transactional
    void init() {
        //necessary users
        cruellaDeVil1 = new User();
        cruellaDeVil1.setName("CruellaDeVil1");
        cruellaDeVil1.setEmail("cruella.de.vil101@evil.com");
        cruellaDeVil1.setRecipes(new ArrayList<>());

        snowWhite2 = new User();
        snowWhite2.setName("SnowWhite2");
        snowWhite2.setEmail("snow.white2@good.com");
        snowWhite2.setRecipes(new ArrayList<>());

        userRepositoryJPA.save(cruellaDeVil1);
        userRepositoryJPA.save(snowWhite2);

        //necessary recipes
        beautyBeast1 = new Recipe();
        beautyBeast1.setPreparation(beautyBeast1Preparation);
        beautyBeast1.setNote(beautyBeast1Note);
        beautyBeast1.setCreator(cruellaDeVil1);
        beautyBeast1.setCreationDate(LocalDate.of(2021,8,15));
        beautyBeast1.setLastEditDate(LocalDate.of(2021,8,16));

        breadedDrawer2 = new Recipe();
        breadedDrawer2.setPreparation(breadedDrawer2Preparation);
        breadedDrawer2.setNote(breadedDrawer2Note);
        breadedDrawer2.setCreator(cruellaDeVil1);
        breadedDrawer2.setCreationDate(LocalDate.of(2021,8,14));
        breadedDrawer2.setLastEditDate(LocalDate.of(2021,8,15));

        hunterGames3 = new Recipe();
        hunterGames3.setPreparation(hunterGames3Preparation);
        hunterGames3.setNote(hunterGames3Note);
        hunterGames3.setCreator(snowWhite2);
        hunterGames3.setCreationDate(LocalDate.of(2021,8,13));
        hunterGames3.setLastEditDate(LocalDate.of(2021,8,14));

        shakeTheMilk4 = new Recipe();
        shakeTheMilk4.setPreparation(shakeTheMilk4Preparation);
        shakeTheMilk4.setNote(shakeTheMilk4Note);
        shakeTheMilk4.setCreator(snowWhite2);
        shakeTheMilk4.setCreationDate(LocalDate.of(2021,8,12));
        shakeTheMilk4.setLastEditDate(LocalDate.of(2021,8,13));

        recipeRepositoryJPA.save(beautyBeast1);
        recipeRepositoryJPA.save(breadedDrawer2);
        recipeRepositoryJPA.save(hunterGames3);
        recipeRepositoryJPA.save(shakeTheMilk4);

        //ratings
        Rating firstUserToThirdRecipe = new Rating();
        firstUserToThirdRecipe.setUser(cruellaDeVil1);
        firstUserToThirdRecipe.setRecipe(hunterGames3);
        firstUserToThirdRecipe.setFingers(6);

        Rating firstUserToFourthRecipe = new Rating();
        firstUserToFourthRecipe.setUser(cruellaDeVil1);
        firstUserToFourthRecipe.setRecipe(shakeTheMilk4);
        firstUserToFourthRecipe.setFingers(7);

        Rating secondUserToFirstRecipe = new Rating();
        secondUserToFirstRecipe.setUser(snowWhite2);
        secondUserToFirstRecipe.setRecipe(beautyBeast1);
        secondUserToFirstRecipe.setFingers(10);

        ratingRepositoryJPA.saveRating(firstUserToThirdRecipe);
        ratingRepositoryJPA.saveRating(firstUserToFourthRecipe);
        ratingRepositoryJPA.saveRating(secondUserToFirstRecipe);

    }

    @Test
    @Order(2)
    @Transactional
    void saveRating() {
        Rating secondUserToSecondRecipe = new Rating();
        User secondUser = userRepositoryJPA.findById(2).get();
        Recipe secondRecipe = recipeRepositoryJPA.findById(2).get();

        secondUserToSecondRecipe.setUser(secondUser);
        secondUserToSecondRecipe.setRecipe(secondRecipe);
        secondUserToSecondRecipe.setFingers(9);

        assertEquals(ratingRepositoryJPA.findByUserAndRecipe(2, 2), Optional.empty());

        Rating saved = ratingRepositoryJPA.saveRating(secondUserToSecondRecipe).get();
        assertEquals(4, saved.getId());
        assertEquals(secondUser, saved.getUser());
        assertEquals(secondRecipe, saved.getRecipe());
        assertEquals(9, saved.getFingers());

        Rating ratingFromRepo = ratingRepositoryJPA.findByUserAndRecipe(2, 2).get();
        assertEquals(4, ratingFromRepo.getId());
        assertEquals(secondUser, ratingFromRepo.getUser());
        assertEquals(secondRecipe, ratingFromRepo.getRecipe());
        assertEquals(9, ratingFromRepo.getFingers());
    }

    @Test
    @Order(3)
    @Transactional
    void getAverageRating() {
        Double average = ratingRepositoryJPA.getAverageRating(1).get();
        assertEquals(10, average);

        Rating newRatingForFirstRecipe = new Rating();
        User firstUser = userRepositoryJPA.findById(1).get();
        Recipe firstRecipe = recipeRepositoryJPA.findById(1).get();

        newRatingForFirstRecipe.setUser(firstUser);
        newRatingForFirstRecipe.setRecipe(firstRecipe);
        newRatingForFirstRecipe.setFingers(9);

        ratingRepositoryJPA.saveRating(newRatingForFirstRecipe);

        Double newAverage = ratingRepositoryJPA.getAverageRating(1).get();
        assertEquals(9.5, newAverage);
    }

    @Test
    @Order(4)
    @Transactional
    void findAllByUser() {
        User firstUser = userRepositoryJPA.findById(1).get();
        Recipe thirdRecipe = recipeRepositoryJPA.findById(3).get();
        Recipe fourthRecipe = recipeRepositoryJPA.findById(4).get();
        Recipe firstRecipe = recipeRepositoryJPA.findById(1).get();

        List<Rating> ratingsFromFirstUser = ratingRepositoryJPA.findAllByUser(firstUser.getId());

        assertEquals(3, ratingsFromFirstUser.size());

        assertEquals(ratingsFromFirstUser.get(0).getId(), 1);
        assertEquals(ratingsFromFirstUser.get(1).getId(), 2);
        assertEquals(ratingsFromFirstUser.get(2).getId(), 5);

        assertEquals(ratingsFromFirstUser.get(0).getUser(), firstUser);
        assertEquals(ratingsFromFirstUser.get(1).getUser(), firstUser);
        assertEquals(ratingsFromFirstUser.get(2).getUser(), firstUser);

        assertEquals(ratingsFromFirstUser.get(0).getRecipe(), thirdRecipe);
        assertEquals(ratingsFromFirstUser.get(1).getRecipe(), fourthRecipe);
        assertEquals(ratingsFromFirstUser.get(2).getRecipe(), firstRecipe);

        assertEquals(ratingsFromFirstUser.get(0).getFingers(), 6);
        assertEquals(ratingsFromFirstUser.get(1).getFingers(), 7);
        assertEquals(ratingsFromFirstUser.get(2).getFingers(), 9);

    }

    @Test
    @Order(5)
    @Transactional
    void findByUserAndRecipe() {
        User firstUser = userRepositoryJPA.findById(1).get();
        Recipe thirdRecipe = recipeRepositoryJPA.findById(3).get();

        Rating rating = ratingRepositoryJPA.findByUserAndRecipe(firstUser.getId(), thirdRecipe.getId()).get();
        assertEquals(1, rating.getId());
        assertEquals(firstUser, rating.getUser());
        assertEquals(thirdRecipe, rating.getRecipe());
        assertEquals(6, rating.getFingers());
    }

    @Test
    @Order(6)
    @Transactional
    void updateRating() {
        User firstUser = userRepositoryJPA.findById(1).get();
        Recipe thirdRecipe = recipeRepositoryJPA.findById(3).get();

        Rating ratingToUpdate = ratingRepositoryJPA.findByUserAndRecipe(firstUser.getId(), thirdRecipe.getId()).get();
        ratingToUpdate.setFingers(3);

        Rating updated = ratingRepositoryJPA.updateRating(ratingToUpdate).get();
        assertEquals(1, updated.getId());
        assertEquals(firstUser, updated.getUser());
        assertEquals(thirdRecipe, updated.getRecipe());
        assertEquals(3, updated.getFingers());
    }

    @Test
    @Order(7)
    @Transactional
    void deleteRating() {
        User firstUser = userRepositoryJPA.findById(1).get();
        Recipe thirdRecipe = recipeRepositoryJPA.findById(3).get();

        Rating ratingToDelete = ratingRepositoryJPA.findByUserAndRecipe(firstUser.getId(), thirdRecipe.getId()).get();

        Rating updated = ratingRepositoryJPA.deleteRating(ratingToDelete).get();
        assertEquals(ratingRepositoryJPA.findByUserAndRecipe(firstUser.getId(), thirdRecipe.getId()), Optional.empty());
    }
}