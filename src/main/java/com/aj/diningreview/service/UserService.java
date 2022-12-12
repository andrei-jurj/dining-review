package com.aj.diningreview.service;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> findAll() throws UserNotFoundException;
    User postUser(User user); //throws UserAlreadyExistsException;
    User putUser(String name, User user);
    User getUserByName(String name) throws UserNotFoundException;
    boolean existsByName(String name);
    Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword);
}