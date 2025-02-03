package org.anonymous;

import org.anonymous.card.services.CardUpdateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardServiceApplicationTests {


	@Autowired
	private CardUpdateService cardUpdateService;
	@Test
	void contextLoads() {

		cardUpdateService.randomCreate(1000);

	}

}
