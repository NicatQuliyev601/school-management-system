package com.nicat.schoolmanagementsystem.repository;



import com.nicat.schoolmanagementsystem.entity.Role;
import com.nicat.schoolmanagementsystem.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
