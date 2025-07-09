package com.renigomes.api_livraria.user_package.user.controller;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.security.SecurityConfig;
import com.renigomes.api_livraria.security.service.TokenService;
import com.renigomes.api_livraria.user_package.user.DTO.*;
import com.renigomes.api_livraria.user_package.user.component.UserComponent;
import com.renigomes.api_livraria.user_package.user.enums.Role;
import com.renigomes.api_livraria.user_package.user.exceptions.UserErrorException;
import com.renigomes.api_livraria.user_package.user.model.User;
import com.renigomes.api_livraria.user_package.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
@AllArgsConstructor
public class UserController {
    public static final String UNEXPECTED_ERROR_USER_DATA_NOT_CHANGED = "Unexpected error. User data not changed !";

    private UserService userService;
    private UserComponent userComponent;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    private static void logErrorMessage() {
        log.error("Unexpected error. Passwords don't match!");
    }

    @Operation(
            summary = "Find user",
            description = "Method to find a user by email",
            tags = "User"
    )
    @GetMapping()
    public ResponseEntity<?> findByEmail(@RequestParam(required = false) String email, HttpServletRequest request){
        User user = (User) userService.findByEmailAuth(email, request);
        if (user == null){
            log.error("User not found");
            throw new UserErrorException("User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(user, UserRespDto.class));
    }

    @Operation(
            summary = "send recovery code",
            description = "method to send the recovery code by email",
            tags = "Auth"
    )
    @PostMapping("/send_recovery_code")
    public ResponseEntity<?> sendRecoveryCode(@RequestBody ReqPasswordRecovery reqEmail) {
        boolean response = userService.passwordRecoveryCode(reqEmail);
        return response ? ResponseEntity.ok(new RespMessage("Code sent")) :
                ResponseEntity.badRequest().body(new RespMessage("Code not sent"));
    }

    @Operation(
            summary = "User Registration",
            description = "Method to register a regular user",
            tags = "Auth"
    )
    @PostMapping("/register")
    public ResponseEntity<?> createUserClient(@RequestBody @Valid UserReqDto userReqDto){
        if (userService.
                findByEmail(userReqDto.getEmail()) != null)
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
        if (userService.findByEmail(userReqDto.getEmail()) != null)
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
            summary = "Recovery code verification",
            method = "Method to verify the recovery code",
            tags = "Auth"
    )
    @PostMapping("/verify_recovery_code")
    public ResponseEntity<RespIsRecoveryCode> verifyRecoveryCode(
            @RequestBody @Valid ReqIsCodeRecoveryCode reqIsCodeRecoveryCode) {
        return ResponseEntity.ok(
                new RespIsRecoveryCode(userService.isRecoveryCode(reqIsCodeRecoveryCode.recoveryCode()))
        );
    }

    @Operation(
            summary = "Change recovery password",
            method = "Method to change recovery password",
            tags = "Auth"
    )
    @PutMapping("/recovery_password")
    public ResponseEntity<?> changeUserRecoveryPassword(@RequestParam String email,
                                                @RequestBody @Valid PasswordEditReqDto passwordEditReqDto){
        if (userService.updateUserPassword(email, passwordEditReqDto)) {
            User user = userComponent.extractUser(email);
            return ResponseEntity.ok(new RespIdDto(user.getId()));
        }
        logErrorMessage();
        throw new UserErrorException("Unexpected error. Passwords don't match!", HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Change password",
            method = "Method to change password",
            tags = "User"
    )
    @PutMapping("/{id}/password")
    public ResponseEntity<?> changeUserPassword(@PathVariable Long id,
                                                @RequestBody @Valid PasswordEditReqDto passwordEditReqDto){
        if (userService.updateUserPassword(id, passwordEditReqDto)) {
            User user = userComponent.extractUser(id);
            return ResponseEntity.ok(new RespIdDto(user.getId()));
        }
        logErrorMessage();
        throw new UserErrorException("Unexpected error. Passwords don't match!", HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Edit User",
            method = "Method to edit a user",
            tags = "User"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserEditReqDTO userEditReqDTO) {
        if (userService.updateUser(id, userEditReqDTO))
            return ResponseEntity.ok(new RespIdDto(userComponent.extractUser(id).getId()));
        log.error(UNEXPECTED_ERROR_USER_DATA_NOT_CHANGED);
        throw new UserErrorException(UNEXPECTED_ERROR_USER_DATA_NOT_CHANGED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Delete User. Attention: This action is irreversible",
            method = "Method to delete a user",
            tags = "User"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
