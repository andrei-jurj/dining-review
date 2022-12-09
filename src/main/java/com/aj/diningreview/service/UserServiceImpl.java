package com.aj.diningreview.service;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    public static final int USERS_PER_PAGE = 6;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() throws UserNotFoundException {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User postUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User putUser(String name, User user) {
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

    @Override
    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    @Override
    public Page<User> listByPage(int pageNum, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
        return userRepository.findAll(pageable);
    }
}