package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserRepositoryJPATest {

    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    @Test
    @Order(1)
    @Transactional
    void init() {
        User cruellaDeVil = new User();
        cruellaDeVil.setName("CruellaDeVil1");
        cruellaDeVil.setEmail("cruella.de.vil101@evil.com");
        cruellaDeVil.setRecipes(new ArrayList<>());


        User snowWhite = new User();
        snowWhite.setName("SnowWhite2");
        snowWhite.setEmail("snow.white2@good.com");
        snowWhite.setRecipes(new ArrayList<>());


        User yarnFairy = new User();
        yarnFairy.setName("YarnFairy3");
        yarnFairy.setEmail("yarn.fairy3@good.com");
        yarnFairy.setRecipes(new ArrayList<>());

        userRepositoryJPA.save(cruellaDeVil);
        userRepositoryJPA.save(snowWhite);
        userRepositoryJPA.save(yarnFairy);
    }

    @Test
    @Order(2)
    @Transactional
    void save() {
        User toSaveDracula = new User();
        toSaveDracula.setName("Dracula4");
        toSaveDracula.setEmail("dracula4@whatever.com");
        toSaveDracula.setRecipes(new ArrayList<>());

        assertEquals(userRepositoryJPA.findById(4), Optional.empty());

        User saved = userRepositoryJPA.save(toSaveDracula).get();
        assertEquals(4, saved.getId());
        assertEquals("Dracula4", saved.getName());
        assertEquals("dracula4@whatever.com", saved.getEmail());
        assertEquals(List.of(), saved.getRecipes());

        User foundById = userRepositoryJPA.findById(4).get();
        assertEquals(4, foundById.getId());
        assertEquals("Dracula4", foundById.getName());
        assertEquals("dracula4@whatever.com", foundById.getEmail());
        assertEquals(List.of(), foundById.getRecipes());
    }

    @Test
    @Order(3)
    @Transactional
    void findAll() {
        List<User> users = userRepositoryJPA.findAll();
        assertEquals(4, users.size());

        assertThat(users.get(0).getId()).isEqualTo(1);
        assertThat(users.get(1).getId()).isEqualTo(2);
        assertThat(users.get(2).getId()).isEqualTo(3);
        assertThat(users.get(3).getId()).isEqualTo(4);

        assertThat(users.get(0).getName()).isEqualTo("CruellaDeVil1");
        assertThat(users.get(1).getName()).isEqualTo("SnowWhite2");
        assertThat(users.get(2).getName()).isEqualTo("YarnFairy3");
        assertThat(users.get(3).getName()).isEqualTo("Dracula4");

        assertThat(users.get(0).getEmail()).isEqualTo("cruella.de.vil101@evil.com");
        assertThat(users.get(1).getEmail()).isEqualTo("snow.white2@good.com");
        assertThat(users.get(2).getEmail()).isEqualTo("yarn.fairy3@good.com");
        assertThat(users.get(3).getEmail()).isEqualTo("dracula4@whatever.com");

        assertThat(users.get(0).getRecipes()).isEmpty();
        assertThat(users.get(1).getRecipes()).isEmpty();
        assertThat(users.get(2).getRecipes()).isEmpty();
        assertThat(users.get(3).getRecipes()).isEmpty();
    }

    @Test
    @Order(4)
    @Transactional
    void findById() {
        User yarnFairyFormRepo = userRepositoryJPA.findById(3).get();
        assertEquals(3, yarnFairyFormRepo.getId());
        assertEquals("YarnFairy3", yarnFairyFormRepo.getName());
        assertEquals("yarn.fairy3@good.com", yarnFairyFormRepo.getEmail());
    }

    @Test
    @Order(5)
    @Transactional
    void findByEmail() {
        User snowWhiteFormRepo = userRepositoryJPA.findByEmail("snow.white2@good.com").get();
        assertEquals(2, snowWhiteFormRepo.getId());
        assertEquals("SnowWhite2", snowWhiteFormRepo.getName());
        assertEquals("snow.white2@good.com", snowWhiteFormRepo.getEmail());
    }

    @Test
    @Order(6)
    @Transactional
    void update() {
        User cruellaToUpdate = new User();
        cruellaToUpdate.setId(1);
        cruellaToUpdate.setName("CruellaDeVil1");
        cruellaToUpdate.setEmail("cruella.de.vil1@evil.com");
        cruellaToUpdate.setRecipes(List.of());

        User cruellaUpdated = userRepositoryJPA.update(cruellaToUpdate).get();
        assertEquals(1, cruellaUpdated.getId());
        assertEquals("CruellaDeVil1", cruellaUpdated.getName());
        assertEquals("cruella.de.vil1@evil.com", cruellaUpdated.getEmail());
        assertThat(cruellaUpdated.getRecipes()).isEmpty();

        User cruellaFromRepo = userRepositoryJPA.findById(1).get();
        assertEquals(1, cruellaFromRepo.getId());
        assertEquals("CruellaDeVil1", cruellaFromRepo.getName());
        assertEquals("cruella.de.vil1@evil.com", cruellaFromRepo.getEmail());
        assertThat(cruellaFromRepo.getRecipes()).isEmpty();
    }

    @Test
    @Order(7)
    @Transactional
    void delete() {
        User draculaToDelete = userRepositoryJPA.findById(4).get();
        userRepositoryJPA.delete(draculaToDelete);
        assertThat(userRepositoryJPA.findById(4)).isEmpty();
    }
}