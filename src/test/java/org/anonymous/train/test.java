package org.anonymous.train;


import org.anonymous.card.services.CardUpdateService;
import org.anonymous.card.services.PredictService;
import org.anonymous.card.services.TrainService;
import org.anonymous.member.MockMember;
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
    private CardUpdateService cardUpdateService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private PredictService predictService;

    @Test
    void contextLoads() {
        cardUpdateService.randomCreate(1000);
    }

    @Test
    void test1() {
        trainService.train();
//        trainService.test();
    }

    @Test
    @MockMember
    void test2() {
        List<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);
        items.add(5);
        System.out.println(items);
        List<Long> test = predictService.predict(items);
        System.out.println(test);
    }
}
