package com.ftn.paymentsystem.repository;

import java.util.Optional;

import com.ftn.paymentsystem.enums.ERole;
import com.ftn.paymentsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}