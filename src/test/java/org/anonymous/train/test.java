package org.anonymous.train;


import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.services.CardUpdateService;
import org.anonymous.card.services.PredictService;
import org.anonymous.card.services.TrainService;
import org.anonymous.card.services.recommend.RecommendInfoService;
import org.anonymous.member.MockMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
@ActiveProfiles({"default", "ml"})
public class test {

    @Autowired
    private CardUpdateService cardUpdateService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private PredictService predictService;

    @Autowired
    private RecommendInfoService recommendInfoService;

    @Test
    void contextLoads() {
        cardUpdateService.randomCreate(5000);
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
        List<CardEntity> test = predictService.predict(items);
        System.out.println(test);
    }

    @Test
    void test3() {
        RecommendCard card = recommendInfoService.getRecommendCard(1L);
        System.out.println(card);
    }
}
