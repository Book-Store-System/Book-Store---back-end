package com.renigomes.api_livraria.unitaries_tests.user.service;

import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.repository.UserRepository;
import com.renigomes.api_livraria.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(value = MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    private static final String EMAIL = "test@gmail.com";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;


    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("Test");
        user.setSurname("123");
        user.setEmail(EMAIL);
        user.setPassword("12345");
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

    }

    @Test
    void save() {
    }

    @Test
    void sizeTable() {
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