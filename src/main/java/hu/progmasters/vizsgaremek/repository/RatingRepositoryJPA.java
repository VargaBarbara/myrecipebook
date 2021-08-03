package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Rating;
import hu.progmasters.vizsgaremek.domain.Receipt;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepositoryJPA implements RatingRepository{

    @Override
    public Rating saveRating(Rating toSave) {
        return null;
    }

    @Override
    public Double getAverageRating(Receipt receipt) {
        return null;
    }

    @Override
    public Rating findById(Integer id) {
        return null;
    }

    @Override
    public Rating updateRating(Rating oldRating, Rating toUpdate) {
        return null;
    }

    @Override
    public Rating deleteRating(Rating toDelete) {
        return null;
    }
}
