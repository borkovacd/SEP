package com.ftn.paymentsystem.repository;

import java.util.Optional;

import com.ftn.paymentsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE (:firstName is '' or u.firstName = null or u.firstName LIKE %:firstName%) AND (:lastName is '' or u.lastName = null or u.lastName LIKE %:lastName%) AND " +
            " (:username is '' or u.username = null or u.username  LIKE %:username%) AND (u.deleted = false) ")
    Page<User> findAllUsers(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("username") String username,
            Pageable pageable);
}