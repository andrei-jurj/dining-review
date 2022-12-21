package com.aj.diningreview.repository;

import com.aj.diningreview.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByName(String name);
    boolean existsByName(String name);
    Long countById(Long id);

    @Query("SELECT u from User u WHERE CONCAT(u.id, ' ', u.name, ' ', u.city, ' ', u.state, ' ', u.email) LIKE %?1%")
    Page<User> findAllSearch(String keyword, Pageable pageable);

    @Query("SELECT u from User u WHERE u.email = :email")
    User getUserByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.name = ?1")
    void updateEnabledStatus(String name, boolean enabled);
}