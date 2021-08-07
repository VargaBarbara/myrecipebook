package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {

    Optional<Recipe> save(Recipe toSave);
    List<Recipe> findAll();
    Optional<Recipe> findById(Integer id);
    List<Recipe> findByUser(Integer userId);
    Optional<Recipe> update(Recipe toUpdate);
    Optional<Recipe> delete(Recipe toDelete);

}
