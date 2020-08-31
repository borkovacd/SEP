package com.ftn.paymentsystem.service;

import com.ftn.paymentsystem.enums.ERole;
import com.ftn.paymentsystem.model.Role;
import com.ftn.paymentsystem.repository.RoleRepository;
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
