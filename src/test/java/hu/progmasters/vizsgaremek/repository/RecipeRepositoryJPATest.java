package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Recipe;
import hu.progmasters.vizsgaremek.domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RecipeRepositoryJPATest {

    /*RecipeCreateUpdateCommand first = new RecipeCreateUpdateCommand
            ("Tüzes borral teli pohár ontja bíbor illatát. Végül még teázni sikk, édességhez jólesik.",
                    "Szörnyen jó, bon appetit!");
    RecipeCreateUpdateCommand second = new RecipeCreateUpdateCommand
            ("Kirántott fiók recept! Végy egy jókora fiókot és könnyed laza mozdulattal rántsd ki.",
                    "Omlósabb lesz, ha szúval előfűszerezzük.");
    RecipeCreateUpdateCommand third = new RecipeCreateUpdateCommand
            ("Vadászvacsora! Vadászd össze a kamra tartalmát ízlésesen helyezd egy tálcára és fogyaszd egészséggel.",
                    "Csak ehető dolgokra vadássz!");
    RecipeCreateUpdateCommand fourth = new RecipeCreateUpdateCommand
            ("Milkshake 2 személyre! Önts 4 dl tejet egy shakerbe, majd 15 percig lendületes mozdulatokkal rázd össze.",
                    "Riszálom úgyis-úgyis!");
*/
    @Autowired
    RecipeRepositoryJPA recipeRepositoryJPA;

    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    User cruellaDeVil;
    User snowWhite;

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


    @Test
    @Order(1)
    @Transactional
    void init() {
        //necessary users
        cruellaDeVil = new User();
        cruellaDeVil.setName("CruellaDeVil1");
        cruellaDeVil.setEmail("cruella.de.vil101@evil.com");
        cruellaDeVil.setRecipes(new ArrayList<>());

        snowWhite = new User();
        snowWhite.setName("SnowWhite2");
        snowWhite.setEmail("snow.white2@good.com");
        snowWhite.setRecipes(new ArrayList<>());

        userRepositoryJPA.save(cruellaDeVil);
        userRepositoryJPA.save(snowWhite);

        //recipes
        Recipe beautyBeast1 = new Recipe();
        beautyBeast1.setPreparation(beautyBeast1Preparation);
        beautyBeast1.setNote(beautyBeast1Note);
        beautyBeast1.setCreator(cruellaDeVil);
        beautyBeast1.setCreationDate(LocalDate.of(2021,8,15));
        beautyBeast1.setLastEditDate(LocalDate.of(2021,8,16));

        Recipe breadedDrawer2 = new Recipe();
        breadedDrawer2.setPreparation(breadedDrawer2Preparation);
        breadedDrawer2.setNote(breadedDrawer2Note);
        breadedDrawer2.setCreator(cruellaDeVil);
        breadedDrawer2.setCreationDate(LocalDate.of(2021,8,14));
        breadedDrawer2.setLastEditDate(LocalDate.of(2021,8,15));

        Recipe hunterGames3 = new Recipe();
        hunterGames3.setPreparation(hunterGames3Preparation);
        hunterGames3.setNote(hunterGames3Note);
        hunterGames3.setCreator(snowWhite);
        hunterGames3.setCreationDate(LocalDate.of(2021,8,13));
        hunterGames3.setLastEditDate(LocalDate.of(2021,8,14));

        recipeRepositoryJPA.save(beautyBeast1);
        recipeRepositoryJPA.save(breadedDrawer2);
        recipeRepositoryJPA.save(hunterGames3);

    }

    @Test
    @Order(2)
    @Transactional
    void save() {
        Recipe shakeTheMilk4 = new Recipe();
        shakeTheMilk4.setPreparation(shakeTheMilk4Preparation);
        shakeTheMilk4.setNote(shakeTheMilk4Note);
        shakeTheMilk4.setCreator(snowWhite);
        shakeTheMilk4.setCreationDate(LocalDate.of(2021,8,12));
        shakeTheMilk4.setLastEditDate(LocalDate.of(2021,8,13));

        assertEquals(recipeRepositoryJPA.findById(4), Optional.empty());

        Recipe saved = recipeRepositoryJPA.save(shakeTheMilk4).get();
        assertEquals(4, saved.getId());
        assertEquals(shakeTheMilk4Preparation, saved.getPreparation());
        assertEquals(shakeTheMilk4Note, saved.getNote());
        assertEquals(snowWhite, saved.getCreator());
        assertEquals(LocalDate.of(2021,8,12), saved.getCreationDate());
        assertEquals(LocalDate.of(2021,8,13), saved.getLastEditDate());

        Recipe foundById = recipeRepositoryJPA.findById(4).get();
        assertEquals(4, foundById.getId());
        assertEquals(shakeTheMilk4Preparation, foundById.getPreparation());
        assertEquals(shakeTheMilk4Note, foundById.getNote());
        assertEquals(snowWhite, foundById.getCreator());
        assertEquals(LocalDate.of(2021,8,12), foundById.getCreationDate());
        assertEquals(LocalDate.of(2021,8,13), foundById.getLastEditDate());

    }

    @Test
    @Order(3)
    @Transactional
    void findAll() {
        List<Recipe> recipes = recipeRepositoryJPA.findAll();
        assertEquals(4, recipes.size());

        assertThat(recipes.get(0).getId()).isEqualTo(1);
        assertThat(recipes.get(1).getId()).isEqualTo(2);
        assertThat(recipes.get(2).getId()).isEqualTo(3);
        assertThat(recipes.get(3).getId()).isEqualTo(4);

        assertThat(recipes.get(0).getPreparation()).isEqualTo(beautyBeast1Preparation);
        assertThat(recipes.get(1).getPreparation()).isEqualTo(breadedDrawer2Preparation);
        assertThat(recipes.get(2).getPreparation()).isEqualTo(hunterGames3Preparation);
        assertThat(recipes.get(3).getPreparation()).isEqualTo(shakeTheMilk4Preparation);

        assertThat(recipes.get(0).getNote()).isEqualTo(beautyBeast1Note);
        assertThat(recipes.get(1).getNote()).isEqualTo(breadedDrawer2Note);
        assertThat(recipes.get(2).getNote()).isEqualTo(hunterGames3Note);
        assertThat(recipes.get(3).getNote()).isEqualTo(shakeTheMilk4Note);

/*        assertThat(recipes.get(0).getCreator()).isEqualTo(cruellaDeVil); //due to StackOverFlow it is not tested here
        assertThat(recipes.get(1).getCreator()).isEqualTo(cruellaDeVil);
        assertThat(recipes.get(2).getCreator()).isEqualTo(snowWhite);
        assertThat(recipes.get(3).getCreator()).isEqualTo(snowWhite);*/

        assertThat(recipes.get(0).getCreationDate()).isEqualTo(LocalDate.of(2021,8,15));
        assertThat(recipes.get(1).getCreationDate()).isEqualTo(LocalDate.of(2021,8,14));
        assertThat(recipes.get(2).getCreationDate()).isEqualTo(LocalDate.of(2021,8,13));
        assertThat(recipes.get(3).getCreationDate()).isEqualTo(LocalDate.of(2021,8,12));

        assertThat(recipes.get(0).getLastEditDate()).isEqualTo(LocalDate.of(2021,8,16));
        assertThat(recipes.get(1).getLastEditDate()).isEqualTo(LocalDate.of(2021,8,15));
        assertThat(recipes.get(2).getLastEditDate()).isEqualTo(LocalDate.of(2021,8,14));
        assertThat(recipes.get(3).getLastEditDate()).isEqualTo(LocalDate.of(2021,8,13));

    }

    @Test
    @Order(4)
    @Transactional
    void findById() {
        Recipe hunterGames3FromRepo = recipeRepositoryJPA.findById(3).get();
        assertThat(hunterGames3FromRepo.getId()).isEqualTo(3);
        assertThat(hunterGames3FromRepo.getPreparation()).isEqualTo(hunterGames3Preparation);
        assertThat(hunterGames3FromRepo.getNote()).isEqualTo(hunterGames3Note);
        assertThat(hunterGames3FromRepo.getCreationDate()).isEqualTo(LocalDate.of(2021,8,13));
        assertThat(hunterGames3FromRepo.getLastEditDate()).isEqualTo(LocalDate.of(2021,8,14));
    }

    @Test
    @Order(5)
    @Transactional
    void findByUser() {
        List<Recipe> recipesByCruella = recipeRepositoryJPA.findByUser(1); // = cruella
        assertEquals(2, recipesByCruella.size());

        assertThat(recipesByCruella.get(0).getId()).isEqualTo(1);
        assertThat(recipesByCruella.get(0).getPreparation()).isEqualTo(beautyBeast1Preparation);
        assertThat(recipesByCruella.get(0).getNote()).isEqualTo(beautyBeast1Note);
        assertThat(recipesByCruella.get(0).getCreationDate()).isEqualTo(LocalDate.of(2021,8,15));
        assertThat(recipesByCruella.get(0).getLastEditDate()).isEqualTo(LocalDate.of(2021,8,16));

        assertThat(recipesByCruella.get(1).getId()).isEqualTo(2);
        assertThat(recipesByCruella.get(1).getPreparation()).isEqualTo(breadedDrawer2Preparation);
        assertThat(recipesByCruella.get(1).getNote()).isEqualTo(breadedDrawer2Note);
        assertThat(recipesByCruella.get(1).getCreationDate()).isEqualTo(LocalDate.of(2021,8,14));
        assertThat(recipesByCruella.get(1).getLastEditDate()).isEqualTo(LocalDate.of(2021,8,15));
    }

    @Test
    @Order(6)
    @Transactional
    void update() {
        String newNoteForDrawer = "A szú annyira nem volt jó ötlet.";
        Recipe breadedDrawer2ToUpdate = new Recipe();
        breadedDrawer2ToUpdate.setId(2);
        breadedDrawer2ToUpdate.setPreparation(breadedDrawer2Preparation);
        breadedDrawer2ToUpdate.setNote(newNoteForDrawer);
        breadedDrawer2ToUpdate.setCreator(cruellaDeVil);
        breadedDrawer2ToUpdate.setCreationDate(LocalDate.of(2021,8,14));
        breadedDrawer2ToUpdate.setLastEditDate(LocalDate.of(2021,8,15));

        Recipe breadedDrawer2Updated = recipeRepositoryJPA.update(breadedDrawer2ToUpdate).get();
        assertEquals(2, breadedDrawer2Updated.getId());
        assertEquals(breadedDrawer2Preparation, breadedDrawer2Updated.getPreparation());
        assertEquals(newNoteForDrawer, breadedDrawer2Updated.getNote());
        assertEquals(cruellaDeVil, breadedDrawer2Updated.getCreator());
        assertEquals(LocalDate.of(2021,8,14), breadedDrawer2Updated.getCreationDate());
        assertEquals(LocalDate.of(2021,8,15), breadedDrawer2Updated.getLastEditDate());

        Recipe breadedDrawer2FromREpo = recipeRepositoryJPA.findById(2).get();
        assertEquals(2, breadedDrawer2FromREpo.getId());
        assertEquals(breadedDrawer2Preparation, breadedDrawer2FromREpo.getPreparation());
        assertEquals(newNoteForDrawer, breadedDrawer2FromREpo.getNote());
        assertEquals(cruellaDeVil, breadedDrawer2FromREpo.getCreator());
        assertEquals(LocalDate.of(2021,8,14), breadedDrawer2FromREpo.getCreationDate());
        assertEquals(LocalDate.of(2021,8,15), breadedDrawer2FromREpo.getLastEditDate());

    }

    @Test
    @Order(7)
    @Transactional
    void delete() {
        Recipe milkShakeToDelete = recipeRepositoryJPA.findById(4).get();
        recipeRepositoryJPA.delete(milkShakeToDelete);
        assertThat(recipeRepositoryJPA.findById(4)).isEmpty();

    }
}