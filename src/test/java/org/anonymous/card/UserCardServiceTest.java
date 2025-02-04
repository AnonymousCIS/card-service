package org.anonymous.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anonymous.card.controllers.CardSearch;
import org.anonymous.card.controllers.RecommendCardSearch;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.entities.UserCardEntity;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.card.repositories.UserCardEntityRepository;
import org.anonymous.card.services.usercard.UserCardInfoService;
import org.anonymous.card.services.usercard.UserCardUpdateService;
import org.anonymous.global.libs.Utils;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.rests.JSONData;
import org.anonymous.member.MockMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.anonymous.card.constants.BankName.TOSS;

@SpringBootTest
public class UserCardServiceTest {
    @Autowired
    private UserCardInfoService infoService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserCardUpdateService updateService;

    private String token;

    @Autowired
    private Utils utils;

    @BeforeEach
    void init() throws JsonProcessingException{
        Map<String, String> cardForm = new HashMap<>();

        cardForm.put("email", "user5@test.org");
        cardForm.put("password", "_aA123456");

        restTemplate = new RestTemplate();

        HttpHeaders _headers = new HttpHeaders();

        HttpEntity<Map<String, String>> request = new HttpEntity<>(cardForm, _headers);

        String apiUrl = utils.serviceUrl("member-service", "/login");

        ResponseEntity<JSONData> item = restTemplate.exchange(apiUrl, HttpMethod.POST, request, JSONData.class);

        token = item.getBody().getData().toString();

        // if (StringUtils.hasText(token)) _headers.setBearerAuth(token);

        System.out.println("token : " + token);
    }

    @Test
    @MockMember
    @DisplayName("유저 카드 서비스")
    void test1() {
        UserCardEntity cards = updateService.update(1L);
        System.out.println(cards);
    }

    @Test
    @MockMember
    @DisplayName("유저 카드 서비스2")
    void test2(){
        List<Long> seqs = List.of(1L, 2L);
        List<UserCardEntity> cards = updateService.update(seqs);
        System.out.println(cards);
    }

    @Test
    @MockMember
    void test3(){
        UserCardEntity item = infoService.get(1L);
        System.out.println(item);
    }
    @Test
    void test4() {
        RecommendCardSearch search = new RecommendCardSearch();
        search.setCardName(List.of("Concrete카드"));
        ListData<UserCardEntity> result = infoService.cardList(search);
        System.out.println(result);
    }
}

