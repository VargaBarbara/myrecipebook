package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.User;

import java.util.List;

public interface UserRepository {

    User save(User toSave);
    List<User> findAll();
    User findById(Integer id);
    User update(User oldUser, User toUpdate);
    User delete(User toDelete);



}
