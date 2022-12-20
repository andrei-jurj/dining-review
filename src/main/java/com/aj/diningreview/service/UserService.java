package com.aj.diningreview.service;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<User> findAll() throws UserNotFoundException;
    void save(User user);
    User putUser(String name, User user);
    User getUserByName(String name) throws UserNotFoundException;
    boolean existsByName(String name);
    Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword);
    boolean isEmailUnique(String name, String email);
    void updateUserEnabledStatus(String name, boolean enabled);

}