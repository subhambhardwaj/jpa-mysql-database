package com.subhamsql.mysqljpa.resource;

import com.subhamsql.mysqljpa.dao.UserRepository;
import com.subhamsql.mysqljpa.model.User;
import com.subhamsql.mysqljpa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public @ResponseBody String addNewUser (@RequestBody UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setLanguage(userDto.getLanguage());
        userRepository.save(user);
        return "Saved";
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateUser (@RequestBody UserDto userUpdate) {
        if(userRepository.existsById(userUpdate.getUsername())) {
            User user = new User();
            user.setUsername(userUpdate.getUsername());
            user.setName(userUpdate.getName());
            user.setEmail(userUpdate.getEmail());
            user.setLanguage(userUpdate.getLanguage());
            userRepository.save(user);
            return "Updated";
        }
        return "No such User found.";
    }

    @GetMapping(path="/search")
    public @ResponseBody List<UserDto> searchUsersByKey (@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String email)  {

        List<User> matchedUsers = new ArrayList<>();
        List<UserDto> matchedUsersDto = new ArrayList<>();

        if (name != null)
            matchedUsers.addAll(userRepository.findByNameContaining(name));
        else if(email != null)
            matchedUsers.addAll(userRepository.findByEmailContaining(email));
        else
            matchedUsers = userRepository.findAll();

        for(User user : matchedUsers) {
            UserDto userDto = new UserDto();
            userDto.setUsername(user.getUsername());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setLanguage(user.getLanguage());
            matchedUsersDto.add(userDto);
        }
        return matchedUsersDto;
    }
}
