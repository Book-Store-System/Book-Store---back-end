package com.renigomes.api_livraria.unitaries_tests.user.service;

import com.renigomes.api_livraria.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.user.DTO.UserRespDto;
import com.renigomes.api_livraria.user.exceptions.UserErrorException;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.repository.UserRepository;
import com.renigomes.api_livraria.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(value = MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String USER_NOT_FOUND = "User not found";
    private static final long LONG_ID = 1L;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user;

    private UserRespDto userRespDto;




    @BeforeEach
    public void setUp() {
        // user
        user = new User();
        user.setId(LONG_ID);
        user.setName("Test");
        user.setSurname("123");
        user.setEmail(EMAIL);
        user.setPassword("12345");

        // user response
        userRespDto = new UserRespDto();
        BeanUtils.copyProperties(user, userRespDto);
        userRespDto.setId(LONG_ID);
    }

    @Test
    void findByEmailAuth() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        UserDetails response = userService.findByEmailAuth(EMAIL);
        assertNotNull(response);
        assertEquals(EMAIL, response.getUsername());
    }

    @Test
    void findById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User response = userService.findById(LONG_ID);
        assertNotNull(response);
        assertEquals(user.getName(), response.getName());
    }
    @Test
    void findByIdWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenThrow(
                new UserErrorException(USER_NOT_FOUND, HttpStatus.NOT_FOUND)
        );
      UserErrorException thrown = assertThrows(
              UserErrorException.class, () -> userService.findById(LONG_ID)
      );
      assertEquals(USER_NOT_FOUND, thrown.getMessage());
      assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());
    }

    @Test
    void save() {
        when(userRepository.save(any())).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserRespDto.class))).thenReturn(userRespDto);
        UserRespDto response = userService.save(user);
        assertNotNull(response);
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getSurname(), response.getSurname());
    }

    @Test
    void sizeTable() {
        when(userRepository.count()).thenReturn(LONG_ID);
        Long response = userService.sizeTable();
        assertNotNull(response);
        assertEquals(LONG_ID, response);
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateUserPassword() {
    }
}