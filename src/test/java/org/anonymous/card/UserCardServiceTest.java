package org.anonymous.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anonymous.card.entities.UserCardEntity;
import org.anonymous.card.repositories.UserCardEntityRepository;
import org.anonymous.card.services.usercard.UserCardInfoService;
import org.anonymous.card.services.usercard.UserCardUpdateService;
import org.anonymous.global.libs.Utils;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserCardServiceTest {
    @Autowired
    private UserCardInfoService infoService;

    @Autowired
    private RestTemplate restTemplate;

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
    void test2(){
        UserCardEntity item = infoService.get(1L);
        System.out.println(item);
    }
}
