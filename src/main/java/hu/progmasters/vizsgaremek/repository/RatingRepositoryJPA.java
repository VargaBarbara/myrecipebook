package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Rating;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class RatingRepositoryJPA implements RatingRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Rating> saveRating(Rating toSave) {
        entityManager.persist(toSave);
        return Optional.of(toSave);
    }

    @Override
    public Optional<Double> getAverageRating(Integer receiptId) {
        Optional<Double> toReturn;
        try {
            toReturn = Optional.of(entityManager.createQuery("SELECT AVG(r.fingers) FROM Rating r " +
                    "WHERE r.receipt.id = :receiptId", Double.class)
                    .setParameter("receiptId", receiptId).getSingleResult());
        } catch (NoResultException | NullPointerException exception) {
            toReturn = Optional.empty();
        }
        return toReturn;
    }

    @Override
    public List<Rating> findAllByUser(Integer userId) {
        return entityManager.createQuery("SELECT r FROM Rating r " +
                "WHERE r.user.id = :userId", Rating.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public Optional<Rating> findByUserAndReceipt(Integer userId, Integer receiptId) {
        Optional<Rating> toReturn;
        try {
            toReturn = Optional.of(entityManager.createQuery("SELECT r FROM Rating r " +
                    "WHERE r.receipt.id = :receiptId AND r.user.id = :userId", Rating.class)
                    .setParameter("receiptId", receiptId).setParameter("userId", userId).getSingleResult());
        } catch (NoResultException noResultException) {
            toReturn = Optional.empty();
        }
        return toReturn;
    }

    @Override
    public Optional<Rating> updateRating(Rating toUpdate) {
        return Optional.of(entityManager.merge(toUpdate));
    }

    @Override
    public Optional<Rating> deleteRating(Rating toDelete) {
        entityManager.remove(toDelete);
        return Optional.of(toDelete);
    }
}
