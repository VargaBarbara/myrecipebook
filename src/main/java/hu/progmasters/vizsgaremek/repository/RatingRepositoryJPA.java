package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Rating;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RatingRepositoryJPA implements RatingRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Rating saveRating(Rating toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    @Override
    public Double getAverageRating(Integer id) {
        return entityManager.createQuery("SELECT AVG(r.fingers) FROM Rating r " +
                "WHERE r.receipt.id = :id", Double.class).getSingleResult();
    }

    @Override
    public Rating findById(Integer id) {
        return entityManager.find(Rating.class, id);
    }

    @Override
    public Rating updateRating(Rating toUpdate) {
        return entityManager.merge(toUpdate);
    }

    @Override
    public Rating deleteRating(Rating toDelete) {
        entityManager.remove(toDelete);
        return toDelete;
    }
}
