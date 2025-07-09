package com.renigomes.api_livraria.user_package.user.component;

import com.renigomes.api_livraria.user_package.user.model.User;
import com.renigomes.api_livraria.user_package.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserComponent {

    private UserRepository userRepository;
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public UserComponent(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    public User extractUser(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User extractUser(String email){
        return (User) userRepository.findByEmail(email);
    }

    public boolean sendEmail(String recipient, String subject, String message) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(recipient);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
