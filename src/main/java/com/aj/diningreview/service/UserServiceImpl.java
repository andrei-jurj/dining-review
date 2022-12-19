package com.aj.diningreview.service;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    public static final int USERS_PER_PAGE = 6;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() throws UserNotFoundException {
        return (List<User>) userRepository.findAll();
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
    public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);

        if (keyword != null) {
            return userRepository.findAllSearch(keyword, pageable);
        }

        return userRepository.findAll(pageable);
    }

    @Override
    public boolean isEmailUnique(String name, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        if (userByEmail == null) {
            return true;
        }

        boolean isCreatingNew = name == null;

        if (isCreatingNew) {
            return userByEmail == null;
        } else {
            if(!Objects.equals(userByEmail.getName(), name)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void save(User user) {
        boolean isUpdatingUser = userRepository.findByName(user.getName()).isPresent();

        if (isUpdatingUser) {
            User existingUser = userRepository.findByName(user.getName()).get();

            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
                user.setId(existingUser.getId());
            } else {
                user.setId(existingUser.getId());
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }

        userRepository.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}