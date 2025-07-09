package com.renigomes.api_livraria.user_package.user.threads;

import com.renigomes.api_livraria.user_package.user.exceptions.CodeGenerationException;
import com.renigomes.api_livraria.user_package.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TimedCodGen extends Thread {

    @Override
    public void run() {
        try {
            Random random = new Random();
            UserService.setRecoveryCode(random.nextInt(1000, 9999));
            sleep(300000);
            UserService.setRecoveryCode(0);
            throw new CodeGenerationException("Code Expired !", HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
