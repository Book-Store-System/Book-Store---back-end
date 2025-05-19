package com.renigomes.api_livraria.user_package.user.service;

import com.renigomes.api_livraria.order_package.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.order_package.purchase_order.service.OrderService;
import com.renigomes.api_livraria.security.component.TokenComponent;
import com.renigomes.api_livraria.security.service.TokenService;
import com.renigomes.api_livraria.user_package.user.DTO.PasswordEditReqDto;
import com.renigomes.api_livraria.user_package.user.DTO.UserEditReqDTO;
import com.renigomes.api_livraria.user_package.user.DTO.UserRespDto;
import com.renigomes.api_livraria.user_package.user.component.UserComponent;
import com.renigomes.api_livraria.user_package.user.enums.Role;
import com.renigomes.api_livraria.user_package.user.exceptions.UnauthorizedUserException;
import com.renigomes.api_livraria.user_package.user.exceptions.UserErrorException;
import com.renigomes.api_livraria.user_package.user.model.User;
import com.renigomes.api_livraria.user_package.user.repository.UserRepository;
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

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private OrderService orderService;
    private UserComponent userComponent;
    private TokenComponent tokenComponent;
    private TokenService tokenService;

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDetails findByEmailAuth(String email, HttpServletRequest request){
        String token = tokenComponent.recorverToken(request);
        String emailUser = tokenService.valueDateToken(token);
        UserDetails getUser = userRepository.findByEmail(emailUser);
        if (email != null && !email.equals(getUser.getUsername())){
            if (((User) getUser).getRole() != Role.ADMIN) {
                log.error("You do not have authorization to proceed");
                throw new UnauthorizedUserException("You do not have authorization to proceed",
                        HttpStatus.FORBIDDEN);
            }
            return userRepository.findByEmail(email);
        }
        return getUser;
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserErrorException("User not found", HttpStatus.NOT_FOUND));
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
    public void deleteUser(Long id){
       User user = userRepository.findById(id).orElseThrow(() -> new UserErrorException("User not found", HttpStatus.NOT_FOUND));
       List<PurchaseOrder> purchaseOrder = orderService.findByUser(user);
       purchaseOrder.forEach(order -> {
            order.setUser(null);
            orderService.save(order);
       });
       userRepository.delete(user);
    }

    @Transactional
    public boolean updateUser(Long id_user, UserEditReqDTO userEditReqDTO){
        User userOld = userComponent.extractUser(id_user);
        BeanUtils.copyProperties(userEditReqDTO, userOld);
        User userEdit =  userRepository.save(userOld);
        return userEdit.getEmail().equals(userEditReqDTO.getEmail()) &&
                userEdit.getName().equals(userEditReqDTO.getName()) &&
                userEdit.getSurname().equals(userEditReqDTO.getSurname());
    }

    @Transactional
    public boolean updateUserPassword(Long id_user, @Valid PasswordEditReqDto passwordEditReqDto){
        User userOld = userComponent.extractUser(id_user);
        if (passwordEditReqDto.newPassword().equals(passwordEditReqDto.repeatNewPassword())){
            userOld.setPassword(passwordEncoder.encode(passwordEditReqDto.newPassword()));
            userOld = userRepository.save(userOld);
            return passwordEncoder.matches(passwordEditReqDto.newPassword(), userOld.getPassword());
        }
        log.error("Passwords don't match!");
        throw new UserErrorException("Passwords do not match !", HttpStatus.BAD_REQUEST);
    }
}
