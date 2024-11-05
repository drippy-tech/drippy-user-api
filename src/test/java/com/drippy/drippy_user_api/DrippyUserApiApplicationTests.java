package com.drippy.drippy_user_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestContainersConfiguration.class)
@SpringBootTest
class DrippyUserApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
