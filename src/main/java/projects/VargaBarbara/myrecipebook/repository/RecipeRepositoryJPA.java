package projects.VargaBarbara.myrecipebook.repository;

import projects.VargaBarbara.myrecipebook.domain.Recipe;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class RecipeRepositoryJPA implements RecipeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Recipe> save(Recipe toSave) {
        entityManager.persist(toSave);
        return Optional.of(toSave);
    }

    @Override
    public List<Recipe> findAll() {
        return entityManager.createQuery("SELECT r FROM Recipe r", Recipe.class).getResultList();
    }

    @Override
    public Optional<Recipe> findById(Integer id) {
        Optional<Recipe> toReturn;
        Recipe recipe = entityManager.find(Recipe.class, id);
        if (recipe == null) {
            toReturn = Optional.empty();
        } else {
            toReturn = Optional.of(recipe);
        }
        return toReturn;
    }

    @Override
    public List<Recipe> findByUser(Integer userId) {
        return entityManager.createQuery("SELECT r FROM Recipe r " +
                "WHERE r.creator.id = :userId", Recipe.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public Optional<Recipe> update(Recipe toUpdate) {
        return Optional.of(entityManager.merge(toUpdate));
    }

    @Override
    public Optional<Recipe> delete(Recipe toDelete) {
        entityManager.remove(toDelete);
        return Optional.of(toDelete);
    }
}
