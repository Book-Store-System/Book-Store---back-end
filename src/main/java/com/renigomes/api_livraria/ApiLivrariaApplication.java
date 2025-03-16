package com.renigomes.api_livraria;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  ApiLivrariaApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("HOST_DB", dotenv.get("HOST_DB"));
		System.setProperty("NAME_DB", dotenv.get("NAME_DB"));
		System.setProperty("USER_DB", dotenv.get("USER_DB"));
		System.setProperty("PASSWORD_DB", dotenv.get("PASSWORD_DB"));
		SpringApplication.run(ApiLivrariaApplication.class, args);
	}
}

