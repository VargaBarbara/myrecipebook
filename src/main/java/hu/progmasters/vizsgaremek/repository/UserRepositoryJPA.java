package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryJPA implements UserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User update(User toUpdate) {
        return entityManager.merge(toUpdate);
    }

    @Override
    public User delete(User toDelete) {
        entityManager.remove(toDelete);
        return toDelete;
    }
}
