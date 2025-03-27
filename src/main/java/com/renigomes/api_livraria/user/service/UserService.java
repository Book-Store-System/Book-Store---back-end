package com.renigomes.api_livraria.user.service;

import com.renigomes.api_livraria.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.purchase_order.service.OrderService;
import com.renigomes.api_livraria.security.service.TokenService;
import com.renigomes.api_livraria.user.DTO.PasswordEditReqDto;
import com.renigomes.api_livraria.user.DTO.UserEditReqDTO;
import com.renigomes.api_livraria.user.DTO.UserRespDto;
import com.renigomes.api_livraria.user.exceptions.UserErrorException;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;
    private final OrderService orderService;

    public UserDetails findByEmailAuth(String email){
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
    public void deleteUser(HttpServletRequest request){
       User user = extractUserByToker(request);
        PurchaseOrder purchaseOrder = orderService.findOrderByUser(user);
        purchaseOrder.setUser(null);
        orderService.save(purchaseOrder);
       userRepository.delete(user);
    }


    @Transactional
    public boolean updateUser(HttpServletRequest request, UserEditReqDTO userEditReqDTO){
        User userOld = extractUserByToker(request);
        BeanUtils.copyProperties(userEditReqDTO, userOld);
        userRepository.save(userOld);
        return userOld.getEmail().equals(userEditReqDTO.getEmail()) &&
                userOld.getName().equals(userEditReqDTO.getName()) &&
                userOld.getSurname().equals(userEditReqDTO.getSurname());
    }

    @Transactional
    public boolean updateUserPassword(HttpServletRequest request, @Valid PasswordEditReqDto passwordEditReqDto){
        User userOld = extractUserByToker(request);
        if (passwordEditReqDto.newPassword().equals(passwordEditReqDto.repeatNewPassword())){
            userOld.setPassword(passwordEncoder.encode(passwordEditReqDto.newPassword()));
            userOld = userRepository.save(userOld);
            return passwordEncoder.matches(passwordEditReqDto.newPassword(), userOld.getPassword());
        }
        log.error("Passwords don't match!");
        throw new UserErrorException("Passwords do not match !", HttpStatus.BAD_REQUEST);
    }

    public User extractUserByToker(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader == null ? null :
                authHeader.replace("Bearer ", "");
        String suject = tokenService.valueDateToken(token);
        return (User) userRepository.findByEmail(suject);

    }
}
