package com.beta.replyservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestServiceApplicationTest {

	@Autowired
	MessageServices messageServices;

	@Test
	public void contextLoads() {
		String message = "13-kbzw9ru";
		Boolean isValid = messageServices.validate(message);

		if (isValid) {
			String resultData = messageServices.getData(message);
			System.out.println("Result : "+ resultData);
		}
		else {
			System.out.println("Invalid format. Please use valid format [eg. XX-dswewew]. XX is number.");
		}
	}

}
