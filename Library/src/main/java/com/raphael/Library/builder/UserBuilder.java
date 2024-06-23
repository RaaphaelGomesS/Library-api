package com.raphael.Library.builder;

import com.raphael.Library.dto.UserDTO;
import com.raphael.Library.entities.User;

public class UserBuilder {

    public static User from(UserDTO userDTO) {

        return User.builder()
                .name(userDTO.getName())
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .build();
    }
}
