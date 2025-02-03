package org.anonymous.card.services;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;
import org.anonymous.card.controllers.RequestCard;
import org.anonymous.card.controllers.RequestUpdateCard;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.exceptions.CardNotFoundException;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.global.exceptions.BadRequestException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Lazy
@Service
@RequiredArgsConstructor
public class CardUpdateService {

    // faker.commerce().material() - 첫번째 후보
    // 이름이랑 설명 진짜 고민 많이 해봐야할듯 하네..
    
    private final CardRepository cardRepository;

    /**
     * 카드 랜덤 생성 -> 건들지 말것.
     */
    public void randomCreate(int count) {

        List<CardEntity> cards = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            CardEntity card = new CardEntity();
            Faker faker = new Faker(Locale.KOREAN);
            Random random = new Random();
            card.setCardName(faker.commerce().material() + "카드"); // 이름 진짜 고민해봐야지 뭔지 모르겠다..
            card.setAnnualFee(random.nextInt(30 * 1000)); // 연회비
            card.setCardType(CardType.values()[random.nextInt(CardType.values().length)]); // 카드타입
            card.setLimit(random.nextLong(100 * 1000000L)); // 카드한도
            card.setCategory(Category.values()[random.nextInt(Category.values().length)]); // 카테고리
            card.setBankName(BankName.values()[random.nextInt(BankName.values().length)]);
            card.setDone(false);


            card.setCardDescription(String.format("%s는 %s카드로 %s에 특화된 혜택을 제공합니다.", card.getCardName(), card.getCardType().getTitle(), card.getCategory().getTitle()));
            card.setItem1_annualFee(card.getAnnualFee());
            card.setItem2_cardType(card.getCardType().getTarget());
            card.setItem3_limit(card.getLimit() / 1000000L);
            card.setItem4_bankName(card.getBankName().getTarget());
            card.setItem5_category(card.getCategory().getTarget());
            card.setOpen(true);


            cards.add(card);
        }

        // region 타겟데이터 필요없어짐

//        int targetAnnualFee = card.getAnnualFee();
//
//        String target = targetAnnualFee <= 10 ? "1" : targetAnnualFee <= 20 ? "2" : targetAnnualFee <= 30 ? "3" : targetAnnualFee <= 40 ? "4" : "5"; // 연회비부터
//
//        Long limit = card.getLimit(); //
//
//        target += card.getCardType();
//        target += limit <= 10 ? "1" : limit <= 20 ? "2" : limit <= 30 ? "3" : limit <= 40 ? "4" : limit <= 50 ? "5" : limit <= 60 ? "6" : limit <= 70 ? "7" : limit <= 80 ? "8" : "9";
//
//        target += card.getCategory();
//        target += card.getBankName();
//
//        int _target = Integer.parseInt(target);
//
//        card.setTarget(_target);

        // endregion

        cardRepository.saveAllAndFlush(cards);

    }

    /**
     * 단일 생성 처리
     * @param card
     * @return
     */
    public CardEntity cardCreate(RequestCard card) {
        CardEntity cardEntity = new CardEntity();

        if (card.getAnnualFee() >= 30000 || card.getAnnualFee() <= 1000 || card.getLimit() <= 1000000L || card.getLimit() >= 100000000L) { // 한도 및 카드 그ㅓ 뭐시기 처리
            throw new BadRequestException();
        }

        cardEntity.setCardName(card.getCardName());
        cardEntity.setBankName(card.getBankName()); // 수치화로 저장
        cardEntity.setAnnualFee(card.getAnnualFee());
        cardEntity.setCardType(card.getCardType()); // 수치화로 저장
        cardEntity.setLimit(card.getLimit());
        cardEntity.setCategory(card.getCategory()); // 수치화로 저장
        cardEntity.setDone(false);
        cardEntity.setCardDescription(card.getCardDescription());
        cardEntity.setOpen(true);

        cardEntity.setItem1_annualFee(card.getAnnualFee());
        cardEntity.setItem2_cardType(card.getCardType().getTarget());
        cardEntity.setItem3_limit(card.getLimit() / 1000000L);
        cardEntity.setItem4_bankName(card.getBankName().getTarget());
        cardEntity.setItem5_category(card.getCategory().getTarget());

        cardRepository.saveAndFlush(cardEntity);

        return cardEntity;
    }
    /**
     * 카드 일괄 수정 처리
     * @param cards
     * @return
     */
    public List<CardEntity> cardUpdates(List<RequestUpdateCard> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new BadRequestException();
        }

        List<CardEntity> cardEntities = new ArrayList<>();
        for (RequestUpdateCard card : cards) {
            CardEntity cardEntity = cardRepository.findByCardName(card.getCardName()).orElseThrow(CardNotFoundException::new);
            addInfo(card, cardEntity);
            cardEntities.add(cardEntity);
        }

        cardRepository.saveAllAndFlush(cardEntities);

        return cardEntities;
    }


    /**
     * 카드 단일 수정 처리
     * @param card
     * @return
     */
    public CardEntity cardUpdate(RequestUpdateCard card) {
        CardEntity cardEntity = cardRepository.findByCardName(card.getCardName()).orElseThrow(CardNotFoundException::new);
        addInfo(card, cardEntity);

        cardRepository.saveAndFlush(cardEntity);

        return cardEntity;
    }

    private void addInfo(RequestUpdateCard card, CardEntity cardEntity) {
        int annualFee = card.getAnnualFee() >= 0 ? card.getAnnualFee() : cardEntity.getAnnualFee();
        cardEntity.setAnnualFee(annualFee);
        cardEntity.setItem1_annualFee(annualFee);

        int cardType = card.getCardType() != null ? card.getCardType().getTarget() : cardEntity.getCardType().getTarget();
        cardEntity.setCardType(card.getCardType());
        cardEntity.setItem2_cardType(cardType);

        Long limit = card.getLimit() >= 0 ? card.getLimit() : cardEntity.getLimit();
        cardEntity.setLimit(limit);
        cardEntity.setItem3_limit(limit);

        int bankName = card.getBankName() != null ? card.getBankName().getTarget() : cardEntity.getBankName().getTarget();
        cardEntity.setItem4_bankName(bankName);
        cardEntity.setBankName(card.getBankName());

        int category = card.getCategory() != null ? card.getCategory().getTarget() : cardEntity.getCategory().getTarget();
        cardEntity.setItem5_category(category);
        cardEntity.setCategory(card.getCategory());

        String cardDescription = StringUtils.hasText(card.getCardDescription()) ? card.getCardDescription() : cardEntity.getCardDescription();
        cardEntity.setCardDescription(cardDescription);

        cardEntity.setDone(false);
        cardEntity.setOpen(card.isOpen());
    }

}






















