package br.ufc.model.dao;

import br.ufc.model.entities.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(String id);
    Iterable<User> findAll();
    void save(User user);
}
