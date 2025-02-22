package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.entities.dto.UserDto;
import com.devsuperior.movieflix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(value = "/profile")
    public ResponseEntity<UserDto> findUserLogged() {
        UserDto obj = userService.findLogged();
        return ResponseEntity.ok().body(obj);
    }

}
