package com.aj.diningreview.service;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers() throws UserNotFoundException;
    User saveUser(User user); //throws UserAlreadyExistsException;
    User updateUser(String name, User user);
    User getUserByName(String name) throws UserNotFoundException;
}