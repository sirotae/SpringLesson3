package lesson3.dao;

import lesson3.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();
    User findUserById(int id);
    User createUser(User user);

}
