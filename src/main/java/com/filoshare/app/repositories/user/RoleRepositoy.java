package com.filoshare.app.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filoshare.app.models.user.Role;

@Repository
public interface RoleRepositoy extends JpaRepository<Role, String> {
    Role findByRole(String roleName);
}
