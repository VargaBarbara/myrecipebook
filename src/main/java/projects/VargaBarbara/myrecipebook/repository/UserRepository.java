package projects.VargaBarbara.myrecipebook.repository;

import projects.VargaBarbara.myrecipebook.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User toSave);
    List<User> findAll();
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    Optional<User> update(User toUpdate);
    Optional<User> delete(User toDelete);



}
