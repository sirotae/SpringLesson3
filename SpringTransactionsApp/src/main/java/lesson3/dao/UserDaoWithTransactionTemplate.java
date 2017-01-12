package lesson3.dao;

import lesson3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.*;
import java.util.List;

//@Primary
@Repository
public class UserDaoWithTransactionTemplate implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate txTemplate;

    @Autowired
    public UserDaoWithTransactionTemplate(JdbcTemplate jdbcTemplate, PlatformTransactionManager txManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.txTemplate = new TransactionTemplate(txManager);
    }

    public List<User> findAll() {
        return this.jdbcTemplate.query(
                "select id, age, name, surname from \"UsersTable\"",
                UserDaoWithTransactionTemplate::mapRow);
    }

    public User findUserById(int id) {
        return this.jdbcTemplate.queryForObject(
                "select id, age, name, surname from \"UsersTable\" where id = ?",
                new Object[]{id},
                UserDaoWithTransactionTemplate::mapRow);
    }

    public User createUser(User user) {
        txTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    internalCreateUser(user);
                } catch (Exception ex) {
                    status.setRollbackOnly();
                }
            }});
        return user;
    }

    private User internalCreateUser(User user) {
        final String sql = "insert into \"UsersTable\" (age, name, surname) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row= this.jdbcTemplate.update(
                new PreparedStatementCreator(){
                    public PreparedStatement createPreparedStatement(Connection connection)
                            throws SQLException {
                        PreparedStatement ps =connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(1, user.getAge());
                        ps.setString(2, user.getName());
                        ps.setString(3, user.getSurname());
                        return ps;
                    }},
                keyHolder);

        Object newPersonId = keyHolder.getKeys().get("id");
        user.setId((Integer)newPersonId);
        return user;
    }

    private static User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
                .id(rs.getInt("id"))
                .age(rs.getInt("age"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .build();
        return user;
    }
}