package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository {

    Optional<Rating> saveRating(Rating toSave);
    Optional<Double> getAverageRating(Integer recipeId);
    List<Rating> findAllByUser(Integer userId);
    Optional<Rating> findByUserAndRecipe(Integer userId, Integer recipeId);
    Optional<Rating> updateRating(Rating toUpdate);
    Optional<Rating> deleteRating(Rating toDelete);
}
