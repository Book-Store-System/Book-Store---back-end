package com.renigomes.api_livraria.user.controller;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.security.SecurityConfig;
import com.renigomes.api_livraria.security.service.TokenService;
import com.renigomes.api_livraria.user.DTO.*;
import com.renigomes.api_livraria.user.enums.Role;
import com.renigomes.api_livraria.user.exceptions.UserErrorException;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@SecurityRequirement(name= SecurityConfig.SECURITY)
@Slf4j
public class UserController {

    public static final String ERROR_PERFORMING_OPERATION = "error performing operation";
    public static final String UNEXPECTED_ERROR_USER_DATA_NOT_CHANGED = "Unexpected error. User data not changed !";
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    private static void perfomingErrorLog() {
        log.error(ERROR_PERFORMING_OPERATION);
    }

    @Operation(
            summary = "Find user",
            description = "Method to find a user by email",
            tags = "User"
    )
    @GetMapping()
    public ResponseEntity<?> findByEmail(@RequestParam String email){
        User user = (User) userService.findByEmailAuth(email);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(modelMapper.map(user, UserRespDto.class));
    }

    @Operation(
            summary = "User Registration",
            description = "Method to register a regular user",
            tags = "Auth"
    )
    @PostMapping("/register")
    public ResponseEntity<?> createUserClient(@RequestBody @Valid UserReqDto userReqDto){
        if (userService.
                findByEmailAuth(userReqDto.getEmail()) != null)
            return ResponseEntity.badRequest().body("This user has already been registered");

        User user = modelMapper.map(userReqDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(userService.sizeTable()==0?Role.ADMIN:Role.CLIENT);

        return ResponseEntity.ok(userService.save(user));
    }
    @Operation(
            summary = "Admin User Registration",
            description = "Method to register a administrator user",
            tags = "Auth"
    )
    @PostMapping("/register/admin")
    public ResponseEntity<?> createUserAdmin(@RequestBody @Valid UserReqDto userReqDto){
        if (userService.findByEmailAuth(userReqDto.getEmail()) != null)
            return ResponseEntity.badRequest().body("This admin has already been registered");

        User user = modelMapper.map(userReqDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);

        return ResponseEntity.ok(userService.save(user));
    }

    @Operation(
            summary = "User Login",
            description = "Method to login",
            tags = "Auth"
    )
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid UserDto userDto){
        UsernamePasswordAuthenticationToken userLogin = new UsernamePasswordAuthenticationToken(
                userDto.email(), userDto.password()
        );
        Authentication authentication = authenticationManager.authenticate(userLogin);
        String token = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new ResponseToken(token));
    }

    @Operation(
            summary = "Active user",
            description = "Method to activate user",
            tags = "User"
    )
    @PatchMapping("/active/{id}")
    public ResponseEntity<Void> activeUser(@PathVariable long id){
        if (userService.activeUser(id) != null)
            return ResponseEntity.noContent().build();
        perfomingErrorLog();
        throw new UserErrorException(ERROR_PERFORMING_OPERATION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

   

    @Operation(
            summary = "Edit User",
            method =  "Method to edit a user",
            tags = "User"
    )
    @PatchMapping
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody @Valid UserEditReqDTO userEditReqDTO){
        if (userService.updateUser(request, userEditReqDTO))
            return ResponseEntity.ok(new RespIdDto(userService.extractUserByToker(request).getId()));
        log.error(UNEXPECTED_ERROR_USER_DATA_NOT_CHANGED);
        throw new UserErrorException(UNEXPECTED_ERROR_USER_DATA_NOT_CHANGED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Change password",
            method = "Method to change password",
            tags = "User"
    )
    @PutMapping("/password")
    public ResponseEntity<?> changeUserPassword(HttpServletRequest request,
                                                @RequestBody @Valid PasswordEditReqDto passwordEditReqDto){
       if(userService.updateUserPassword(request, passwordEditReqDto)){
           User user = userService.extractUserByToker(request);
           return ResponseEntity.ok(new RespIdDto(user.getId()));
       }
       log.error("Unexpected error. Passwords don't match!");
       throw new UserErrorException("Unexpected error. Passwords don't match!", HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Delete User",
            method = "Method to delete a user",
            tags = "User"
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(HttpServletRequest request){
        if (userService.deactivateUser(request) != null)
            return ResponseEntity.noContent().build();
        perfomingErrorLog();
        throw new UserErrorException(ERROR_PERFORMING_OPERATION, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Operation(
            summary = "Delete User Administrador",
            method = "Method to delete a user",
            tags = "User"
    )
    @DeleteMapping("/{id}/admin")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id){
        if (userService.deactivateUserAdmin(id) != null)
            return ResponseEntity.noContent().build();
        perfomingErrorLog();
        throw new UserErrorException(ERROR_PERFORMING_OPERATION, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
