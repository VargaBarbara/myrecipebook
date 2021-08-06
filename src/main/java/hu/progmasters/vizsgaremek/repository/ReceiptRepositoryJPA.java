package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Receipt;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ReceiptRepositoryJPA implements ReceiptRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Receipt> save(Receipt toSave) {
        entityManager.persist(toSave);
        return Optional.of(toSave);
    }

    @Override
    public List<Receipt> findAll() {
        return entityManager.createQuery("SELECT r FROM Receipt r", Receipt.class).getResultList();
    }

    @Override
    public Optional<Receipt> findById(Integer id) {
        Optional<Receipt> toReturn;
        Receipt receipt = entityManager.find(Receipt.class, id);
        if (receipt == null) {
            toReturn = Optional.empty();
        } else {
            toReturn = Optional.of(receipt);
        }
        return toReturn;
    }

    @Override
    public List<Receipt> findByUser(Integer userId) {
        return entityManager.createQuery("SELECT r FROM Receipt r " +
                "WHERE r.creator.id = :userId", Receipt.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public Optional<Receipt> update(Receipt toUpdate) {
        return Optional.of(entityManager.merge(toUpdate));
    }

    @Override
    public Optional<Receipt> delete(Receipt toDelete) {
        entityManager.remove(toDelete);
        return Optional.of(toDelete);
    }
}
