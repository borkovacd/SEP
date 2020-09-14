package com.ftn.scientificcentral.service;

import com.ftn.scientificcentral.enums.ERole;
import com.ftn.scientificcentral.model.Role;
import com.ftn.scientificcentral.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(ERole role) {
        return roleRepository.findByName(role);
    }
}
