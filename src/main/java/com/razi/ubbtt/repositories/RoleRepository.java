package com.razi.ubbtt.repositories;

import com.razi.ubbtt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}