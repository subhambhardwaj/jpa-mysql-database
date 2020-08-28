package com.subhamsql.mysqljpa.resource;

import com.subhamsql.mysqljpa.dao.UserRepository;
import com.subhamsql.mysqljpa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/demo")
public class UserResource {

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path="/add")
    public @ResponseBody User addNewUser (@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping(path = "/update")
    public @ResponseBody User updateUser (@RequestBody User userUpdate) {
        return userRepository.save(userUpdate);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path="/emailsearch")
    public @ResponseBody List<User> getUserByEmail(@RequestParam String email)  {
        return userRepository.findByEmailContaining(email);
    }

    @GetMapping(path="/namesearch")
    public @ResponseBody List<User> getUsersByName(@RequestParam String name)  {
        return userRepository.findByNameContaining(name);
    }
}
