package com.app.perfumeshop;

import com.app.perfumeshop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PerfumeshopApplicationTests {

	@MockBean
	private UserService userService;

	@Test
	void contextLoads() {
		userService.init();
	}

}
