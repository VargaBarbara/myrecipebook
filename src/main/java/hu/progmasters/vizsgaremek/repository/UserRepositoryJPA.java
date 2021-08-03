package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryJPA implements UserRepository{
    @Override
    public User save(User toSave) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public User update(User oldUser, User toUpdate) {
        return null;
    }

    @Override
    public User delete(User toDelete) {
        return null;
    }
}
