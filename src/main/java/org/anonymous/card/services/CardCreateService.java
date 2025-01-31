package org.anonymous.card.services;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.repositories.CardRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Lazy
@Service
@RequiredArgsConstructor
public class CardCreateService {

    // faker.commerce().material() - 첫번째 후보
    // 이름이랑 설명 진짜 고민 많이 해봐야할듯 하네..
    
    private final CardRepository cardRepository;

    public void randomCreate() {
        CardEntity card = new CardEntity();
        Faker faker = new Faker(Locale.KOREAN);
        Random random = new Random();
        card.setCardName(faker.commerce().material() + "카드"); // 이름 진짜 고민해봐야지 뭔지 모르겠다..
        card.setAnnualFee(random.nextInt(50)); // 연회비
        card.setCardType(CardType.values()[random.nextInt(CardType.values().length)].getTarget()); // 카드타입
        card.setLimit(random.nextLong(100)); // 카드한도
        card.setCategory(Category.values()[random.nextInt(Category.values().length)].getTarget()); // 카테고리
        card.setBackName(BankName.values()[random.nextInt(BankName.values().length)].getTarget());
        card.setDone(false);

        int targetAnnualFee = card.getAnnualFee();

        String target = targetAnnualFee <= 10 ? "1" : targetAnnualFee <= 20 ? "2" : targetAnnualFee <= 30 ? "3" : targetAnnualFee <= 40 ? "4" : "5"; // 연회비부터

        Long limit = card.getLimit(); //

        target += card.getCardType();
        target += limit <= 10 ? "1" : limit <= 20 ? "2" : limit <= 30 ? "3" : limit <= 40 ? "4" : limit <= 50 ? "5" : limit <= 60 ? "6" : limit <= 70 ? "7" : limit <= 80 ? "8" : "9";

        target += card.getCategory();
        target += card.getBackName();

        int _target = Integer.parseInt(target);

        card.setTarget(_target);
        cardRepository.saveAndFlush(card);

    }
}






















