package org.anonymous.card.controllers;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.entities.UserCardEntity;
import org.anonymous.card.services.CardInfoService;
import org.anonymous.card.services.recommend.RecommendInfoService;
import org.anonymous.card.services.usercard.UserCardDeleteService;
import org.anonymous.card.services.usercard.UserCardInfoService;
import org.anonymous.card.services.usercard.UserCardUpdateService;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
/**
 * 일반 유저 & 관리자 공용
 */
public class CardController {


    private final CardInfoService cardInfoService;
    private final RecommendInfoService recommendInfoService;
    private final UserCardInfoService userCardInfoService;
    private final UserCardUpdateService userCardUpdateService;
    private final UserCardDeleteService userCardDeleteService;

    /**
     * 단일 조회 - 추천받은 내역 조회
     */
    @GetMapping("/recommend/view/{seq}")
    public JSONData recommendInfo(@PathVariable("seq") Long seq) {
        RecommendCard card = recommendInfoService.getRecommendCard(seq);
        return new JSONData(card);
    }

    /**
     * 목록 조회 - 추천받은 내역 조회 -> 페이지네이션 필요
     */
    @GetMapping("/recommend/list")
    public JSONData recommendList(RecommendCardSearch search) {
        ListData<RecommendCard> items = recommendInfoService.cardList(search);
        return new JSONData(items);
    }


    // 2. Card Entity R - (단일, 목록 조회(Pagination)) - GetMapping

    /**
     * 단일 조회 card
     * @return
     */
    @GetMapping("/card/view/{seq}")
    public JSONData cardInfo(@PathVariable("seq") Long seq) {
        CardEntity card = cardInfoService.getCardInfo(seq);
        return new JSONData(card);
    }

    /**
     * 목록 조회 - 카드
     * @return
     */
    @GetMapping("/card/list")
    public JSONData cardList(CardSearch cardSearch) {

        ListData<CardEntity> items = cardInfoService.cardList(cardSearch);

        return new JSONData(items);
    }

    // 3. UserCard C - (단일) - PostMapping
    // R - (단일, 목록 조회(Pagination) - GetMapping
    // U - 유저 삭제(deletedAt을 현재 시간으로 하고 조회에서 제외) - PatchMapping

    /**
     * 유저 카드 단일, 목록 생성
     * @param card
     * @return
     */
    @PostMapping("/user/create")
    public JSONData createCard(@RequestBody List<Long> card) {

        List<UserCardEntity> cards = userCardUpdateService.update(card);
        return new JSONData(cards);
    }

    /**
     * user Card 단일 조회
     * @return
     */
    @GetMapping("/user/view/{seq}")
    public JSONData userCardInfo(@PathVariable("seq") Long seq) {
        UserCardEntity card = userCardInfoService.get(seq);
        return new JSONData(card);
    }


    /**
     * user card 목록 조회
     * @return
     */
    @GetMapping("/user/list")
    public JSONData userCardList(RecommendCardSearch search) {
        ListData<UserCardEntity> cards = userCardInfoService.cardList(search);
        return new JSONData(cards);
    }

    /**
     * 유저 카드 목록 삭제 - deleteAt Update
     * @return
     */
    @PatchMapping("/user/deletes")
    public JSONData deleteUserCards(List<Long> seq) {
        List<UserCardEntity> cards = userCardDeleteService.deletes(seq, false, "remove");
        return new JSONData(cards);
    }
}























