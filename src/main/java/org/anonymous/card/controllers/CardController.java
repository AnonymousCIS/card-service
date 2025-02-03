package org.anonymous.card.controllers;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.services.CardInfoService;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
/**
 * 일반 유저 & 관리자 공용
 */
public class CardController {


    private final CardInfoService cardInfoService;

    /**
     * 단일 조회 - 추천받은 내역 조회
     */
    @GetMapping("/recommend/info")
    public JSONData recommendInfo() {

        return null;
    }

    /**
     * 목록 조회 - 추천받은 내역 조회 -> 페이지네이션 필요
     */
    @GetMapping("/recommend/list")
    public JSONData recommendList() {

        return null;
    }


    // 2. Card Entity R - (단일, 목록 조회(Pagination)) - GetMapping

    /**
     * 단일 조회 card
     * @return
     */
    @GetMapping("/card/info")
    public JSONData cardInfo(@RequestParam("seq") Long seq) {
        CardEntity card = cardInfoService.getCardInfo(seq);
        return new JSONData(card);
    }

    /**
     * 목록 조회 - 카드
     * @return
     */
    @GetMapping("/card/list")
    public ListData<CardEntity> cardList(CardSearch cardSearch) {
        return cardInfoService.cardList(cardSearch);
    }

    // 3. UserCard C - (단일) - PostMapping
    // R - (단일, 목록 조회(Pagination) - GetMapping
    // U - 유저 삭제(deletedAt을 현재 시간으로 하고 조회에서 제외) - PatchMapping

    /**
     * user Card 단일 조회
     * @return
     */
    @GetMapping("/card/user/info")
    public JSONData userCardInfo() {
        
        return null;
    }


    /**
     * user card 목록 조회
     * @return
     */
    @GetMapping("/card/user/list")
    public JSONData userCardList() {
        
        return null;
    }

    /**
     * 유저 카드 단일 삭제 - deleteAt Update
     * @return
     */
    @PatchMapping("/card/user/delete")
    public JSONData deleteUserCard() {
        
        
        return null;
    }

    /**
     * 유저 카드 목록 삭제 - deleteAt Update
     * @return
     */
    @PatchMapping("/card/user/deletes")
    public JSONData deleteUserCards() {
        
        return null;
    }
    
    
    
}























