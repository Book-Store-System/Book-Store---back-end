package com.renigomes.api_livraria.user_package.user.repository;

import com.renigomes.api_livraria.user_package.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

}