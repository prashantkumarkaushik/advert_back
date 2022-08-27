package com.prashant.BlogApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.BlogApp.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
    
}
