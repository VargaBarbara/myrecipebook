package projects.VargaBarbara.myrecipebook.repository;

import projects.VargaBarbara.myrecipebook.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJPA implements UserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> save(User toSave) {
        entityManager.persist(toSave);
        return Optional.of(toSave);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Integer id) {
        Optional<User> toReturn;
        User user = entityManager.find(User.class, id);
        if (user == null) {
            toReturn = Optional.empty();
        } else {
            toReturn = Optional.of(user);
        }
        return toReturn;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> toReturn;
        try {
            User user = entityManager.createQuery("SELECT u FROM User u " +
                    "WHERE u.email = :email", User.class).setParameter("email", email).getSingleResult();
            toReturn = Optional.of(user);
        } catch (NoResultException | NullPointerException exception) {
            toReturn = Optional.empty();
        }
        return toReturn;
    }

    @Override
    public Optional<User> update(User toUpdate) {
        return Optional.of(entityManager.merge(toUpdate));
    }

    @Override
    public Optional<User> delete(User toDelete) {
        entityManager.remove(toDelete);
        return Optional.of(toDelete);
    }
}
