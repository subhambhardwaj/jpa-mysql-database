package com.subhamsql.mysqljpa.resource;

import com.subhamsql.mysqljpa.dao.UserRepository;
import com.subhamsql.mysqljpa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path="/demo")
public class UserResource {

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestBody User user) {
        userRepository.save(user);
        return "Saved";
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateUser (@RequestBody User userUpdate) {
        if(userRepository.existsById(userUpdate.getUsername())) {
            userRepository.save(userUpdate);
            return "Updated";
        }
        return "No such User found.";
    }

    @GetMapping(path="/search")
    public @ResponseBody List<String> searchUsersByKey (@RequestParam String key)  {
        Set<User> matchedUsers = new HashSet<>();
        List<String> matchedUserNames = new ArrayList<>();

        if(key.length() == 0)
            matchedUsers.addAll(userRepository.findAll());

        else if(!userRepository.findByEmailContaining(key).isEmpty())
            matchedUsers.addAll(userRepository.findByEmailContaining(key));
        else if (!userRepository.findByNameContaining(key).isEmpty())
            matchedUsers.addAll(userRepository.findByNameContaining(key));
        else{
            matchedUserNames.add("No user found");
            return matchedUserNames;
        }

        for(User user : matchedUsers)
            matchedUserNames.add(user.getName());

        return matchedUserNames;
    }
}
