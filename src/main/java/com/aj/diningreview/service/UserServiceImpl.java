package com.aj.diningreview.service;

import com.aj.diningreview.exception.UserNotFoundException;
import com.aj.diningreview.model.User;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() throws UserNotFoundException {
        return userRepository.findAll();
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
    public List<User> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<User> pagedResult = userRepository.findAll(paging);

        return pagedResult.toList();
    }

    public Page<User> findPaginated(Pageable pageable) {
        List<User> users = userRepository.findAll();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;

        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), users.size());
    }
}