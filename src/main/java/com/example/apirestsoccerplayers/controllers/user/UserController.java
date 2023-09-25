package com.example.apirestsoccerplayers.controllers.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apirestsoccerplayers.handlers.Result;
import com.example.apirestsoccerplayers.handlers.StatusCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path="/auth/add")
    public Result addUser(@Valid @RequestBody User request){
        User user = userService.addUser(request);
        return new Result(true, StatusCode.SUCCESS, "saves successfully", user);
    }

    @GetMapping(path="/{username}")
    public Result getUser(@PathVariable(value="username") String username){
        User user = (User) userService.loadUserByUsername(username);
        return new Result(true, StatusCode.SUCCESS, "user found", user);
    }

    @DeleteMapping(path="/auth/delete/{id}")
    public Result deleteUser(@PathVariable(value="id") Integer id){
        String message = userService.deleteUser(id);
        return new Result(true, StatusCode.SUCCESS, message);
    }
}
