package com.subhamsql.mysqljpa.dao;

import com.subhamsql.mysqljpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByEmailContaining(String email);

    List<User> findByNameContaining(String name);
}