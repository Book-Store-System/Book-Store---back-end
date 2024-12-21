package com.renigomes.api_livraria.user.service;

import com.renigomes.api_livraria.user.DTO.UserRespDto;
import com.renigomes.api_livraria.user.enums.Status;
import com.renigomes.api_livraria.user.exceptions.UserNotFoundException;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.repository.UserRepository;
import com.renigomes.api_livraria.user.util.Utilites;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Utilites utilites;

    public UserDetails findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserRespDto save(User user){
        User readerSave = userRepository.save(user);
        return modelMapper.map(readerSave, UserRespDto.class);
    }

    public Long sizeTable(){
        return userRepository.count();
    }

    @Transactional
    public UserDetails deactivateUser(HttpServletRequest request){
       User user = utilites.extractUserByToker(request);
       user.setStatus(Status.INACTIVE);
       return userRepository.save(user);
    }

    @Transactional
    public User activeUser(long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            user.get().setStatus(Status.ACTIVE);
            return userRepository.save(user.get());
        }
        log.error("User not found !");
        throw new UserNotFoundException("User not found !", HttpStatus.BAD_REQUEST);
    }
}
