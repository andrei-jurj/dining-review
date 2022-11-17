package com.aj.diningreview.service;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String name, User user) {
        Optional<User> userData = userRepository.findByName(name);

        if (userData.isPresent()) {
            User updatedUser = userData.get();
            updatedUser.setCity(user.getCity());
            updatedUser.setState(user.getState());
            updatedUser.setZipCode(user.getZipCode());
            updatedUser.setHasPeanutAllergy(user.getHasPeanutAllergy());
            updatedUser.setHasEggAllergy(user.getHasEggAllergy());
            updatedUser.setHasDairyAllergy(user.getHasDairyAllergy());
            return userRepository.save(updatedUser);
        }

        return null;
    }

    @Override
    public User getUserByName(String name) throws UserNotFoundException {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException(name));
    }
}