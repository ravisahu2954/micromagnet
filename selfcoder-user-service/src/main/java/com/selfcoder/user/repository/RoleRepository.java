package com.selfcoder.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfcoder.user.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
