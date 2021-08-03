package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Rating;
import hu.progmasters.vizsgaremek.domain.Receipt;

public interface RatingRepository {

    Rating saveRating(Rating toSave);
    Double getAverageRating(Receipt receipt);
    Rating findById(Integer id);
    Rating updateRating(Rating oldRating, Rating toUpdate);
    Rating deleteRating(Rating toDelete);
}
