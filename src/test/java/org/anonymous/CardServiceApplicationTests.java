package org.anonymous;

import org.anonymous.card.services.CardCreateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class CardServiceApplicationTests {


	@Autowired
	private CardCreateService cardCreateService;
	@Test
	void contextLoads() {

		for (int i = 0; i < 1000; i++) {
			cardCreateService.randomCreate();
		}

	}

}
