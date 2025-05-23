package com.renigomes.api_livraria.user_package.user.component;

import com.renigomes.api_livraria.user_package.user.model.User;
import com.renigomes.api_livraria.user_package.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserComponent {

    private final UserRepository userRepository;

    public User extractUser(Long id){
        return userRepository.findById(id).orElse(null);

    }
}
