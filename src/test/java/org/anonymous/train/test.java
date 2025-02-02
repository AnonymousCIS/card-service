package org.anonymous.train;


import org.anonymous.card.services.CardCreateService;
import org.anonymous.card.services.PredictService;
import org.anonymous.card.services.TrainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles({"default", "ml"})
public class test {

    @Autowired
    private CardCreateService cardCreateService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private PredictService predictService;

    @Test
    void contextLoads() {

        for (int i = 0; i < 10000; i++) {
            cardCreateService.randomCreate();
        }

    }

    @Test
    void test1() {
        trainService.train();
//        trainService.test();
    }

    @Test
    void test2() {
        List<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);
        items.add(5);
        System.out.println(items);
        List<Integer> test = predictService.predict(items);
        System.out.println(test);
    }
}
