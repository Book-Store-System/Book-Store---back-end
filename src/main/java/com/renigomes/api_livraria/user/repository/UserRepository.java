package com.renigomes.api_livraria.user.repository;

import com.renigomes.api_livraria.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmailAuth(String email);
    Optional<User> findByEmail(String email);

}