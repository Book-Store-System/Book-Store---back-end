package com.renigomes.api_livraria;
import com.renigomes.api_livraria.enums.EnvEnum;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  ApiLivrariaApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		for (EnvEnum env : EnvEnum.values()) {
			System.setProperty(env.value , dotenv.get(env.value));
		}
		SpringApplication.run(ApiLivrariaApplication.class, args);
	}
}

