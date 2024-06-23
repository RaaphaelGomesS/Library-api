package com.raphael.Library.service;

import com.raphael.Library.builder.UserBuilder;
import com.raphael.Library.dto.UserDTO;
import com.raphael.Library.entities.User;
import com.raphael.Library.exception.UserException;
import com.raphael.Library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) throws UserException {

        Optional<User> optionalUser = userRepository.findByLogin(userDTO.getLogin());

        if (optionalUser.isPresent()) {
            throw new UserException("Login already exist!", HttpStatus.CONFLICT);
        }

        User newUser = UserBuilder.from(userDTO);

        userRepository.save(newUser);

        return newUser;
    }
}
