package org.anonymous.card.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.entities.UserCardEntity;
import org.anonymous.card.services.CardDeleteService;
import org.anonymous.card.services.CardUpdateService;
import org.anonymous.card.services.recommend.RecommendDeleteService;
import org.anonymous.card.services.usercard.UserCardDeleteService;
import org.anonymous.card.validators.CardValidator;
import org.anonymous.global.exceptions.BadRequestException;
import org.anonymous.global.libs.Utils;
import org.anonymous.global.rests.JSONData;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
/**
 * 관리자 권한쪽
 */
public class AdminCardController {

    private final CardUpdateService cardUpdateService;
    private final Utils utils;
    private final CardValidator cardValidator;
    private final CardDeleteService cardDeleteService;
    private final RecommendDeleteService recommendDeleteService;
    private final UserCardDeleteService userCardDeleteService;

    // 1. RecommendCard D - (단일, 목록 일괄 처리) - DeleteMapping

    /**\
     * 추천내역 단일, 목록 삭제
     * @return
     */
    @DeleteMapping("/recommend/remove")
    public JSONData recommendDelete(@RequestParam("seq") List<Long> seq) {
        List<RecommendCard> cards = recommendDeleteService.deletes(seq, false, "remove");
        return new JSONData(cards);
    }

    // 2. Card Entity C - (단일) - PostMapping
    // U - (단일, 목록 일괄 처리) - PatchMapping
    // D - (단일, 목록 일괄 처리) - DeleteMapping

    /**
     * 카드 단일 생성
     * @return
     */
    @PostMapping("/create")
    public JSONData createCards(@RequestBody @Valid RequestCard card, Errors errors) {

        cardValidator.validate(card, errors);
        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        CardEntity cardEntity = cardUpdateService.cardCreate(card);

        return new JSONData(cardEntity);
    }

    /**
     * 카드 일괄 수정 처리
     * @return
     */
    @PatchMapping("/updates")
    public JSONData updateCards(@RequestBody List<RequestUpdateCard> cards) {
        List<CardEntity> cardEntities = cardUpdateService.cardUpdates(cards);
        return new JSONData(cardEntities);
    }

    // region 필요없는거

//    @PatchMapping("/update")
//    public JSONData updateCard(@RequestBody RequestUpdateCard cards) {
//        CardEntity cardEntity = cardUpdateService.cardUpdate(cards);
//        return new JSONData(cardEntity);
//    }

//    /**
//     * 카드 일괄 오픈 수정 - deleteAt Update
//     * @return
//     */
//    @PatchMapping("/deletes")
//    public JSONData deleteCards(@RequestParam("seq") List<Long> seq) {
//        List<CardEntity> cardEntities = cardDeleteService.deletes(seq, false,"delete");
//        return new JSONData(cardEntities);
//    }

    // endregion

    /**
     * 카드 단일, 일괄 찐 삭제
     * @return
     */
    @DeleteMapping("/removes")
    public JSONData removeCards(@RequestParam("seq") List<Long> seq) {
        List<CardEntity> cardEntities = cardDeleteService.deletes(seq, false, "remove");
        return new JSONData(cardEntities);
    }


    // 3. UserCard D - (단일, 목록 일괄 처리) - DeleteMapping

    /**
     * 유저 카드 단일, 일괄 찐 삭제
     * @return
     */
    @DeleteMapping("/user/removes")
    public JSONData removeUsers(@RequestParam("seq") List<Long> seq) {
        List<UserCardEntity> userCardEntities = userCardDeleteService.deletes(seq, false, "remove");
        return new JSONData(userCardEntities);
    }
}




















