package org.anonymous.card.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "AdminCard", description = "관리자용 카드 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
/**
 * 관리자 권한쪽
 */
public class AdminCardController {

    private final Utils utils;
    private final CardValidator cardValidator;
    private final CardUpdateService cardUpdateService;
    private final CardDeleteService cardDeleteService;
    private final UserCardDeleteService userCardDeleteService;
    private final RecommendDeleteService recommendDeleteService;

    // 1. RecommendCard D - (단일, 목록 일괄 처리) - DeleteMapping

    /**\
     * 추천내역 단일, 목록 삭제
     * @return
     */
    @Operation(summary = "유저 추천내역 단일, 목록 삭제", method = "DELETE", description = "유저 추천내역 카드 삭제. DB내에서 삭제된다.")
    @ApiResponse(responseCode = "200")
    @Parameter(name="seq", description = "유저 카드 번호")
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
    @Operation(summary = "카드 단일 생성", method = "POST", description = "카드 하나를 생성한다.")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name = "cardName", description = "카드이름", required = true),
            @Parameter(name = "annualFee", description = "연회비", required = true, example = "1000 ~ 30000"),
            @Parameter(name="CardType", description = "카드타입", required = true, examples = {
                    @ExampleObject(name = "개인체크", value = "PersonalCheck"),
                    @ExampleObject(name = "개인신용", value = "PersonalCredit"),
                    @ExampleObject(name = "법인체크", value = "CorporateCheck"),
                    @ExampleObject(name = "법인신용", value = "CorporateCredit")
            }),
            @Parameter(name = "limit", description = "카드 한도", required = true),
            @Parameter(name = "BankName", description = "은행이름", required = true, examples = {
                    @ExampleObject(name = "한국은행", value = "HANKUK"),
                    @ExampleObject(name = "국민은행", value = "KB"),
                    @ExampleObject(name = "제일은행", value = "SC"),
                    @ExampleObject(name = "한국시티은행", value = "CITY"),
                    @ExampleObject(name = "하나은행", value = "HANA"),
                    @ExampleObject(name = "신한은행", value = "SHINHAN"),
                    @ExampleObject(name = "K-뱅크", value = "KBANK"),
                    @ExampleObject(name = "카카오은행", value = "KAKAO"),
                    @ExampleObject(name = "토스은행", value = "TOSS"),
                    @ExampleObject(name = "수협은행", value = "SUHYUP"),
                    @ExampleObject(name = "부산은행", value = "BUSAN"),
                    @ExampleObject(name = "경남은행", value = "KYUNGNAM"),
                    @ExampleObject(name = "광주은행", value = "KYANGJOO"),
                    @ExampleObject(name = "전북은행", value = "JUNBOK"),
                    @ExampleObject(name = "제주은행", value = "JEJOO"),
                    @ExampleObject(name = "롯데카드", value = "LOTTE"),
                    @ExampleObject(name = "농협은행", value = "NONGHYUP"),
                    @ExampleObject(name = "삼성카드", value = "SAMSUNG"),
                    @ExampleObject(name = "현대카드", value = "HYUNDAI"),
                    @ExampleObject(name = "우리은행", value = "WOORI"),
                    @ExampleObject(name = "신협은행", value = "SINHYUP"),
                    @ExampleObject(name = "새마을금고", value = "SAEMAEULGEUMGO"),
                    @ExampleObject(name = "우체국", value = "WOOCAEKUK")
            }),
            @Parameter(name="Category", description = "카테고리", required = true, examples = {
                    @ExampleObject(name = "SHOPPING", value = "SHOPPING"),
                    @ExampleObject(name = "LIFE", value = "LIFE"),
                    @ExampleObject(name = "TRAVEL", value = "TRAVEL"),
                    @ExampleObject(name = "LIVING", value = "LIVING")
            }),
            @Parameter(name = "cardDescription", description = "카드설명", required = true),
    })
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
    @Operation(summary = "카드 일괄 수정", method = "POST", description = "카드 여러개를 수정한다.")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name = "cardName", description = "카드이름"),
            @Parameter(name = "annualFee", description = "연회비", example = "1000 ~ 30000"),
            @Parameter(name="CardType", description = "카드타입", examples = {
                    @ExampleObject(name = "개인체크"),
                    @ExampleObject(name = "개인신용"),
                    @ExampleObject(name = "법인체크"),
                    @ExampleObject(name = "법인신용")
            }),
            @Parameter(name = "limit", description = "카드 한도"),
            @Parameter(name = "BankName", description = "은행이름", examples = {
                    @ExampleObject(name = "한국은행", value = "HANKUK"),
                    @ExampleObject(name = "국민은행", value = "KB"),
                    @ExampleObject(name = "제일은행", value = "SC"),
                    @ExampleObject(name = "한국시티은행", value = "CITY"),
                    @ExampleObject(name = "하나은행", value = "HANA"),
                    @ExampleObject(name = "신한은행", value = "SHINHAN"),
                    @ExampleObject(name = "K-뱅크", value = "KBANK"),
                    @ExampleObject(name = "카카오은행", value = "KAKAO"),
                    @ExampleObject(name = "토스은행", value = "TOSS"),
                    @ExampleObject(name = "수협은행", value = "SUHYUP"),
                    @ExampleObject(name = "부산은행", value = "BUSAN"),
                    @ExampleObject(name = "경남은행", value = "KYUNGNAM"),
                    @ExampleObject(name = "광주은행", value = "KYANGJOO"),
                    @ExampleObject(name = "전북은행", value = "JUNBOK"),
                    @ExampleObject(name = "제주은행", value = "JEJOO"),
                    @ExampleObject(name = "롯데카드", value = "LOTTE"),
                    @ExampleObject(name = "농협은행", value = "NONGHYUP"),
                    @ExampleObject(name = "삼성카드", value = "SAMSUNG"),
                    @ExampleObject(name = "현대카드", value = "HYUNDAI"),
                    @ExampleObject(name = "우리은행", value = "WOORI"),
                    @ExampleObject(name = "신협은행", value = "SINHYUP"),
                    @ExampleObject(name = "새마을금고", value = "SAEMAEULGEUMGO"),
                    @ExampleObject(name = "우체국", value = "WOOCAEKUK")
            }),
            @Parameter(name="Category", description = "카테고리", examples = {
                    @ExampleObject(name = "SHOPPING", value = "SHOPPING"),
                    @ExampleObject(name = "LIFE", value = "LIFE"),
                    @ExampleObject(name = "TRAVEL", value = "TRAVEL"),
                    @ExampleObject(name = "LIVING", value = "LIVING")
            }),
            @Parameter(name = "cardDescription", description = "카드설명"),
            @Parameter(name = "isOpen", description = "카드 OPEN"),
    })
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
     * 카드 단일, 일괄 삭제
     * @return
     */
    @Operation(summary = "카드 단일, 일괄 삭제", description = "카드 DB내에서 삭제", method = "DELETE")
    @Parameter(name = "seq", required = true, description = "카드번호")
    @ApiResponse(responseCode = "200")
    @DeleteMapping("/removes")
    public JSONData removeCards(@RequestParam("seq") List<Long> seq) {
        List<CardEntity> cardEntities = cardDeleteService.deletes(seq, false, "remove");
        return new JSONData(cardEntities);
    }


    // 3. UserCard D - (단일, 목록 일괄 처리) - DeleteMapping

    /**
     * 유저 카드 단일
     * @return
     */
    @Operation(summary = "유저 카드 단일, 일괄 삭제", description = "유저 카드 DB내에서 삭제", method = "DELETE")
    @Parameter(name = "seq", required = true, description = "카드번호")
    @ApiResponse(responseCode = "200")
    @DeleteMapping("/user/removes")
    public JSONData removeUsers(@RequestParam("seq") List<Long> seq) {
        List<UserCardEntity> userCardEntities = userCardDeleteService.deletes(seq, false, "remove");
        return new JSONData(userCardEntities);
    }
}




















