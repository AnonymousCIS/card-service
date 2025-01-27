package org.anonymous.train;


import org.anonymous.card.services.CardCreateService;
import org.anonymous.card.services.TrainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"default", "ml"})
public class test {

    @Autowired
    private CardCreateService cardCreateService;

    @Autowired
    private TrainService trainService;

    @Test
    void contextLoads() {

        for (int i = 0; i < 100000; i++) {
            cardCreateService.randomCreate();
        }

    }

    @Test
    void test1() {
        trainService.train();
    }
}
