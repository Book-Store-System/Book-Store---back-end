package com.renigomes.api_livraria.unitaries_tests.user.repository;

import com.renigomes.api_livraria.user.enums.Role;
import com.renigomes.api_livraria.user.model.User;
import com.renigomes.api_livraria.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoryUserTest {

    private static final String EMAIL = "test@gmail.com";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;



    @Test
    @DisplayName("findByEmail_WhenUserExists_ReturnUserDetails")
    public void findByEmail_WhenUserExists_ReturnUserDetails() {
        User user = new User();
        user.setName("Test");
        user.setSurname("123");
        user.setEmail(EMAIL);
        user.setPassword("12345");
        user.setRole(Role.CLIENT);
        testEntityManager.persist(user); // Persist the user in the database
        testEntityManager.flush(); // Imediately sicronize the database with the entity manager

        UserDetails response = userRepository.findByEmail(EMAIL);

        assertNotNull(response);
        assertEquals(response.getUsername(), user.getEmail());
    }

    @Test
    @DisplayName("findByEmail_WhenUserNotExists_ReturnNull")
    public void findByEmail_WhenUserNotExists_ReturnNull(){

        UserDetails response = userRepository.findByEmail(EMAIL);

        assertNull(response);
    }


}
