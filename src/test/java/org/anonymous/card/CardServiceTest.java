package org.anonymous.card;

import com.netflix.discovery.converters.Auto;
import org.anonymous.card.controllers.CardSearch;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.card.services.CardDeleteService;
import org.anonymous.card.services.CardInfoService;
import org.anonymous.card.services.CardUpdateService;
import org.anonymous.global.paging.ListData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.anonymous.card.constants.BankName.TOSS;

@SpringBootTest
public class CardServiceTest {

    @Autowired
    private CardUpdateService cardUpdateService;

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardDeleteService cardDeleteService;


    @Test
    void Test1() {
        CardEntity item = cardInfoService.getCardInfo(1L);
        System.out.println(item);
    }

    @Test
    void Test2() {
        CardSearch search = new CardSearch();
        search.setBankName(Collections.singletonList(TOSS));
        ListData<CardEntity> result = cardInfoService.cardList(search);
        System.out.println(result);
    }
    //    @Test
//    void Test3(){
//        CardEntity item = cardDeleteService.delete(1L, false, "remove");
//        System.out.println(item);
//    }

}

