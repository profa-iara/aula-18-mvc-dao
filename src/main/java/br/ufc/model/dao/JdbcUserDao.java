package br.ufc.model.dao;

import br.ufc.model.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbc;

    public JdbcUserDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<User> findById(String id) {

        var results = jdbc.query(
                "select id, username, password from Users where id=?",
                this::mapRowToUser,
                id
        );

        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    @Override
    public Iterable<User> findAll() {
        return jdbc.query(
                "select id, username, password from Users",
                this::mapRowToUser
        );
    }

    @Override
    public void save(User user) {
        jdbc.update(
                "insert into Users (id, username, password) values (?, ?, ?)",
                user.id(),
                user.username(),
                user.password()
        );
    }

    private User mapRowToUser(ResultSet row, int rowNum)
            throws SQLException {

        return new User(
                row.getString("id"),
                row.getString("username"),
                row.getString("password")
        );

    }
}
