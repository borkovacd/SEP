package com.ftn.scientificcentral.repository;

import java.util.Optional;

import com.ftn.scientificcentral.enums.ERole;
import com.ftn.scientificcentral.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}