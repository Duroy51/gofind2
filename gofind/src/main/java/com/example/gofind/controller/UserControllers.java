package com.example.gofind.controller;

import com.example.gofind.models.User;
import com.example.gofind.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserControllers {


    @Autowired
    private UserService userService;


    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }



    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") final Long id) {
        Optional<User> user = userService.getUser(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }


    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable("id") final Long id, @RequestBody User user) {
        Optional<User> e = userService.getUser(id);
        if(e.isPresent()) {
            User currentUser = e.get();

            String name = user.getName();
            if(name != null) {
                currentUser.setName(name);
            }


            String surname = user.getSurname();
            if(surname != null) {
                currentUser.setSurname(surname);
            }


            String numeroCNI = user.getNumeroCNI();
            if(numeroCNI != null) {
                currentUser.setNumeroCNI(numeroCNI);
            }





            userService.saveUser(currentUser);
            return currentUser;
        } else {
            return null;
        }
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") final Long id) {
        userService.deleteUser(id);
    }
}
