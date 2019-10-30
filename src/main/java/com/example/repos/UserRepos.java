package com.example.repos;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, Integer> {

    User findByUsername(String Username);

    User findByActivationCode(String code);

    User findByEmail(String email);
}
