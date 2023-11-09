package br.ufc.controller;

import br.ufc.model.entities.User;
import br.ufc.model.dao.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateUserDTO createUserDTO) {

        var user = new User(
                UUID.randomUUID().toString(),
                createUserDTO.username(),
                createUserDTO.password()
        );

        userDao.save(user);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDto) {
        var user = userDao.findById(id).orElseThrow();
        userDao.save(new User(user.id(), user.username(), updateUserDto.password()));
    }

    @GetMapping
    public Iterable<User> findAll() {
        return userDao.findAll();
    }

}
