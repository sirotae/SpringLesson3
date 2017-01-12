package lesson3.controllers;

import lesson3.dao.UserDao;
import lesson3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return userDao.findUserById(id);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userDao.createUser(user);
        return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
    }

}
