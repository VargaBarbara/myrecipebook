package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Rating;

public interface RatingRepository {

    Rating saveRating(Rating toSave);
    Double getAverageRating(Integer id);
    Rating findById(Integer id);
    Rating updateRating(Rating toUpdate);
    Rating deleteRating(Rating toDelete);
}
