package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Receipt;
import hu.progmasters.vizsgaremek.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReceiptRepositoryJPA implements ReceiptRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Receipt save(Receipt toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    @Override
    public List<Receipt> findAll() {
        return entityManager.createQuery("SELECT r FROM Receipt r", Receipt.class).getResultList();
    }

    @Override
    public Receipt findById(Integer id) {
        return entityManager.find(Receipt.class, id);
    }

    @Override
    public List<Receipt> findByUser(Integer userId) {
        return entityManager.createQuery("SELECT r FROM Receipt r " +
                "WHERE r.creator.id = :userId", Receipt.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public Receipt update(Receipt toUpdate) {
        return entityManager.merge(toUpdate);   //TODO saveOrUpdate in Service
    }

    @Override
    public Receipt delete(Receipt toDelete) {
        entityManager.remove(toDelete);
        return toDelete;
    }
}
