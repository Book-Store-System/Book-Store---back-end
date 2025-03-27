package com.renigomes.api_livraria.user.service;

import com.renigomes.api_livraria.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.purchase_order.service.OrderService;
import com.renigomes.api_livraria.security.service.TokenService;
import com.renigomes.api_livraria.user.DTO.PasswordEditReqDto;
import com.renigomes.api_livraria.user.DTO.UserEditReqDTO;
import com.renigomes.api_livraria.user.DTO.UserRespDto;
import com.renigomes.api_livraria.user.component.UserComponent;
import com.renigomes.api_livraria.user.exceptions.UserErrorException;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
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
    private PasswordEncoder passwordEncoder;
    private final OrderService orderService;
    private final UserComponent userComponent;

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
       User user = userComponent.extractUserByToker(request);
        List<PurchaseOrder> purchaseOrder = orderService.findOrderByUser(user);
        purchaseOrder.forEach(order -> {
            order.setUser(null);
            orderService.save(order);
        });
       userRepository.delete(user);
    }


    @Transactional
    public boolean updateUser(HttpServletRequest request, UserEditReqDTO userEditReqDTO){
        User userOld = userComponent.extractUserByToker(request);
        BeanUtils.copyProperties(userEditReqDTO, userOld);
        userRepository.save(userOld);
        return userOld.getEmail().equals(userEditReqDTO.getEmail()) &&
                userOld.getName().equals(userEditReqDTO.getName()) &&
                userOld.getSurname().equals(userEditReqDTO.getSurname());
    }

    @Transactional
    public boolean updateUserPassword(HttpServletRequest request, @Valid PasswordEditReqDto passwordEditReqDto){
        User userOld = userComponent.extractUserByToker(request);
        if (passwordEditReqDto.newPassword().equals(passwordEditReqDto.repeatNewPassword())){
            userOld.setPassword(passwordEncoder.encode(passwordEditReqDto.newPassword()));
            userOld = userRepository.save(userOld);
            return passwordEncoder.matches(passwordEditReqDto.newPassword(), userOld.getPassword());
        }
        log.error("Passwords don't match!");
        throw new UserErrorException("Passwords do not match !", HttpStatus.BAD_REQUEST);
    }
}
