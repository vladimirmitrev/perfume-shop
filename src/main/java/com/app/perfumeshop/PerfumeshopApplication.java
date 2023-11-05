package com.app.perfumeshop;

import com.app.perfumeshop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PerfumeshopApplication implements CommandLineRunner {

	private final UserService userService;

	public PerfumeshopApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(PerfumeshopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userService.init();
	}
}
