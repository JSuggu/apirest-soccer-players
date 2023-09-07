package com.example.apirestsoccerplayers.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path="/auth/add")
    public String addUser(@RequestBody User request){
        return userService.addUser(request);
    }

    @GetMapping(path="/{username}")
    public String getUser(@PathVariable(value="username") String username){
        return userService.loadUserByUsername(username) != null ? "Connection successfully" : "User or Password not valid";
    }

    @DeleteMapping(path="/auth/delete/{id}")
    public String deleteUser(@PathVariable(value="id") Integer id){
        return userService.deleteUser(id);
    }
}
