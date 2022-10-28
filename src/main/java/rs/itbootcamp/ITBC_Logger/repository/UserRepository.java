package rs.itbootcamp.ITBC_Logger.repository;

import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rs.itbootcamp.ITBC_Logger.models.Log;
import rs.itbootcamp.ITBC_Logger.models.User;


import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertUser(User user) {
        String query = "INSERT INTO Clients (id, username, password, email, userType) VALUES ('" + user.getId().toString() + "','"
                + user.getUsername() + "','" + user.getPassword() + "','" + user.getEmail() + "','" + user.getUserType() + "')";
        jdbcTemplate.execute(query);
    }

    public List<User> getAllUsers() {
        String query = "SELECT id, username, email, userType FROM Clients";

        return jdbcTemplate.query(
                query,
                BeanPropertyRowMapper.newInstance(User.class)
        );
    }
}
