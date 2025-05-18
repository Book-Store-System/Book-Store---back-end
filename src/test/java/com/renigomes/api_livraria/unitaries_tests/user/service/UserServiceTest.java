package com.renigomes.api_livraria.unitaries_tests.user.service;

import com.renigomes.api_livraria.order_package.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.order_package.purchase_order.service.OrderService;
import com.renigomes.api_livraria.user_package.user.DTO.PasswordEditReqDto;
import com.renigomes.api_livraria.user_package.user.DTO.UserEditReqDTO;
import com.renigomes.api_livraria.user_package.user.DTO.UserRespDto;
import com.renigomes.api_livraria.user_package.user.component.UserComponent;
import com.renigomes.api_livraria.user_package.user.exceptions.UserErrorException;
import com.renigomes.api_livraria.user_package.user.model.User;
import com.renigomes.api_livraria.user_package.user.repository.UserRepository;
import com.renigomes.api_livraria.user_package.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String USER_NOT_FOUND = "User not found";
    private static final long LONG_ID = 1L;
    public static final String PASSWORD = "12345";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private OrderService orderService;
    @Mock
    private UserComponent userComponent;

    private User user, userEdit;

    private UserRespDto userRespDto;

    private PurchaseOrder order1, order2;
    private UserEditReqDTO userEditReqDTO;

    private List<PurchaseOrder> orders;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this);
        // user
        user = new User();
        user.setId(LONG_ID);
        user.setName("Test");
        user.setSurname("123");
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        // user response
        userRespDto = new UserRespDto();
        BeanUtils.copyProperties(user, userRespDto);
        userRespDto.setId(LONG_ID);

        // orders
        order1 = new PurchaseOrder();
        order1.setUser(user);
        order2 = new PurchaseOrder();
        order2.setUser(user);
        orders = Arrays.asList(order1, order2);

        //UserEditReqDto
        userEditReqDTO = new UserEditReqDTO(
                "test",
                "edit",
                "test@teste.com"
        );

        //UserEdit
        userEdit = new User();
        userEdit.setId(LONG_ID);
        userEdit.setName(userEditReqDTO.getName());
        userEdit.setSurname(userEditReqDTO.getSurname());
        userEdit.setEmail(userEditReqDTO.getEmail());

        //PasswordEditReqDto
        PasswordEditReqDto passwordEditReqDto = new PasswordEditReqDto(
                PASSWORD, PASSWORD
        );
    }

    @Test
    @DisplayName("findByEmailAuth_WhenUserExists_ReturnUserDetails")
    void findByEmailAuth() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        UserDetails response = userService.findByEmailAuth(EMAIL);
        assertNotNull(response);
        assertEquals(EMAIL, response.getUsername());
    }

    @Test
    @DisplayName("findById_WhenUserExists_ReturnUser")
    void findById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User response = userService.findById(LONG_ID);
        assertNotNull(response);
        assertEquals(user.getName(), response.getName());
    }

    @Test
    @DisplayName("findById_WhenUserNotFound_ThrowUserErrorException")
    void findByIdWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenThrow(
                new UserErrorException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        UserErrorException thrown = assertThrows(
                UserErrorException.class, () -> userService.findById(LONG_ID));
        assertEquals(USER_NOT_FOUND, thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());
    }

    @Test
    @DisplayName("save_WhenUserIsSaved_ReturnUserRespDto")
    void save() {
        when(userRepository.save(any())).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserRespDto.class))).thenReturn(userRespDto);
        UserRespDto response = userService.save(user);
        assertNotNull(response);
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getSurname(), response.getSurname());
    }

    @Test
    @DisplayName("sizeTable_WhenUserExists_ReturnLong")
    void sizeTable() {
        when(userRepository.count()).thenReturn(LONG_ID);
        Long response = userService.sizeTable();
        assertNotNull(response);
        assertEquals(LONG_ID, response);
    }

    @Test
    @DisplayName("deleteUser_WhenUserExists_DeleteUser")
    void deleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderService.findByUser(user)).thenReturn(orders);
        userService.deleteUser(LONG_ID);
        verify(userRepository, times(1)).findById(anyLong());
        verify(orderService, times(1)).findByUser(user);
        verify(orderService, times(2)).save(any(PurchaseOrder.class));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("deleteUser_WhenUserNotFound_ThrowUserErrorException")
    void deleteUserWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenThrow(
                new UserErrorException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        UserErrorException thrown = assertThrows(
                UserErrorException.class, () -> userService.deleteUser(LONG_ID));
        assertEquals(USER_NOT_FOUND, thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("updateUser_WhenUserExists_ReturnBoolean")
    void updateUser() {
        when(userComponent.extractUser(LONG_ID)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(userEdit);
        User userOld = new User();
        BeanUtils.copyProperties(userEditReqDTO, userOld);
        assertEquals(userEditReqDTO.getName(), userOld.getName());
        assertEquals(userEditReqDTO.getSurname(), userOld.getSurname());
        assertEquals(userEditReqDTO.getEmail(), userOld.getEmail());
        boolean response = userService.updateUser(LONG_ID, userEditReqDTO);
        assertTrue(response);
        verify(userComponent, times(1)).extractUser(LONG_ID);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("updateUser_WhenUserNotFound_ThrowUserErrorException")
    void updateUserPassword() {
    }
}