package com.aj.diningreview.repository;

import com.aj.diningreview.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByName(String name);
    boolean existsByName(String name);
}