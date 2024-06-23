package com.raphael.Library.controller;

import com.raphael.Library.dto.UserDTO;
import com.raphael.Library.entities.User;
import com.raphael.Library.exception.UserException;
import com.raphael.Library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) throws UserException {

        User user = userService.createUser(userDTO);

        return ResponseEntity.ok(user);

    }
}
