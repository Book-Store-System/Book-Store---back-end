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
@Tag(name="user")
@SecurityRequirement(name= SecurityConfig.SECURITY)
@Slf4j
public class UserController {

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

    @Operation(
            summary = "Find user",
            description = "Method to find a user by email"
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
            description = "Method to register a regular user"
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
            description = "Method to register a administrator user"
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
            description = "Method to login"
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
            description = "Method to activate user"
    )
    @PatchMapping("/{id}/active_user")
    public ResponseEntity<Void> activeUser(@PathVariable long id){
        if (userService.activeUser(id) != null)
            return ResponseEntity.noContent().build();
        log.error("error performing operation");
        throw new UserErrorException("error performing operation", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Edit User",
            method =  "Method to edit a user"
    )
    @PatchMapping("/edit_user/{id_user}")
    public ResponseEntity<?> updateUser(@PathVariable long id_user, @RequestBody @Valid UserEditReqDTO userEditReqDTO){
        if (userService.updateUser(id_user, userEditReqDTO))
            return ResponseEntity.ok(new RespIdDto(id_user));
        log.error("User not found !");
        throw new UserErrorException("User not found !", HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Change password",
            method = "Method to change password"
    )
    @PutMapping("/edit_user/{id_user}/password")
    public ResponseEntity<?> changeUserPassword(@PathVariable long id_user,
                                                @RequestBody @Valid PasswordEditReqDto passwordEditReqDto){
       if(userService.updateUserPassword(id_user, passwordEditReqDto))
           return ResponseEntity.ok(new RespIdDto(id_user));
       log.error("User not found !");
       throw new UserErrorException("User not found !", HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Delete User",
            method = "Method to delete a user"
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(HttpServletRequest request){
        if (userService.deactivateUser(request) != null)
            return ResponseEntity.noContent().build();
        log.error("error performing operation");
        throw new UserErrorException("error performing operation", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
