package com.subhamsql.mysqljpa.dao;

import com.subhamsql.mysqljpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {

    Set<User> findByEmailContaining(String email);

    Set<User> findByNameContaining(String name);
}