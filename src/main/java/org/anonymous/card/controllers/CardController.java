package org.anonymous.card.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.anonymous.member.MemberUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "card", description = "카드 관리 API")
@RestController
@RequiredArgsConstructor
/**
 * 일반 유저 & 관리자 공용
 */
public class CardController {


    private final MemberUtil memberUtil;
    private final CardInfoService cardInfoService;
    private final RecommendInfoService recommendInfoService;
    private final UserCardInfoService userCardInfoService;
    private final UserCardUpdateService userCardUpdateService;
    private final UserCardDeleteService userCardDeleteService;

    /**
     * 단일 조회 - 추천받은 내역 조회
     */
    @Operation(summary = "추천받은 내역 단일 조회", method="GET", description = "유저가 추천받은 내역을 조회한다.")
    @ApiResponse(responseCode = "200")
    @Parameter(name="seq", description = "번호")
    @GetMapping("/recommend/view/{seq}")
    public JSONData recommendInfo(@PathVariable("seq") Long seq) {
        RecommendCard card = recommendInfoService.getRecommendCard(seq);
        return new JSONData(card);
    }

    /**
     * 목록 조회 - 추천받은 내역 조회 -> 페이지네이션 필요
     */
    @Operation(summary = "유저 추천 받은 내역 조회", method="GET", description = "data - 조회된 추천받은 카드 목록, pagination - 페이징 기초 데이터")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name="page", description = "페이지 번호", example = "1"),
            @Parameter(name="limit", description = "한페이지당 레코드 갯수", example = "20"),
            @Parameter(name="sopt", description = "검색옵션", example = "ALL"),
            @Parameter(name="skey", description = "검색키워드"),
            @Parameter(name="email", description = "이메일별로 검색"),
            @Parameter(name="CardName", description = "카드이름 별 검색"),
            @Parameter(name = "BankName", description = "은행이름 별 검색", examples = {
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
            @Parameter(name="cardLimitMax", description = "최대한도"),
            @Parameter(name="cardLimitMin", description = "최소한도"),
            @Parameter(name="Category", description = "카테고리별 검색", examples = {
                    @ExampleObject(name = "SHOPPING", value = "SHOPPING"),
                    @ExampleObject(name = "LIFE", value = "LIFE"),
                    @ExampleObject(name = "TRAVEL", value = "TRAVEL"),
                    @ExampleObject(name = "LIVING", value = "LIVING")
            }), // 여기 날짜 뭐뭐있는지 설명
            @Parameter(name="CardType", description = "카드타입별 검색", examples = {
                    @ExampleObject(name = "개인체크", value = "PersonalCheck"),
                    @ExampleObject(name = "개인신용", value = "PersonalCredit"),
                    @ExampleObject(name = "법인체크", value = "CorporateCheck"),
                    @ExampleObject(name = "법인신용", value = "CorporateCredit")
            }),
            @Parameter(name="dateType", description = "날짜별 검색", examples = {
                    @ExampleObject(name = "createdAt", value = "생성날짜기준"),
                    @ExampleObject(name = "modifiedAt", value = "수정일 기준"),
                    @ExampleObject(name = "deleteAt", value = "삭제날짜 기준")
            }), // 여기 날짜 뭐뭐있는지 설명
            @Parameter(name="sDate", description = "시작날짜"),
            @Parameter(name="dDate", description = "끝날짜"),
    })
    @GetMapping("/recommend/list")
    public JSONData recommendList(@ModelAttribute RecommendCardSearch search) {
        ListData<RecommendCard> items = recommendInfoService.cardList(search);
        return new JSONData(items);
    }


    // 2. Card Entity R - (단일, 목록 조회(Pagination)) - GetMapping

    /**
     * 단일 조회 card
     * @return
     */
    @Operation(summary = "카드 조회", method="GET", description = "추천 카드 조회")
    @ApiResponse(responseCode = "200")
    @Parameter(name="seq", description = "번호")
    @GetMapping("/view/{seq}")
    public JSONData cardInfo(@PathVariable("seq") Long seq) {
        CardEntity card = cardInfoService.getCardInfo(seq);
        return new JSONData(card);
    }

    /**
     * 목록 조회 - 카드
     * @return
     */
    @Operation(summary = "카드 목록 조회", method="GET", description = "data - 카드 목록, pagination - 페이징 기초 데이터")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name="page", description = "페이지 번호", example = "1"),
            @Parameter(name="limit", description = "한페이지당 레코드 갯수", example = "20"),
            @Parameter(name="sopt", description = "검색옵션", example = "ALL"),
            @Parameter(name="skey", description = "검색키워드"),
            @Parameter(name="CardName", description = "카드이름 별 검색"),
            @Parameter(name = "BankName", description = "은행이름 별 검색", examples = {
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
            @Parameter(name="cardLimitMax", description = "최대한도"),
            @Parameter(name="cardLimitMin", description = "최소한도"),
            @Parameter(name="Category", description = "카테고리별 검색", examples = {
                    @ExampleObject(name = "SHOPPING", value = "SHOPPING"),
                    @ExampleObject(name = "LIFE", value = "LIFE"),
                    @ExampleObject(name = "TRAVEL", value = "TRAVEL"),
                    @ExampleObject(name = "LIVING", value = "LIVING")
            }), // 여기 날짜 뭐뭐있는지 설명
            @Parameter(name="CardType", description = "카드타입별 검색", examples = {
                    @ExampleObject(name = "개인체크", value = "PersonalCheck"),
                    @ExampleObject(name = "개인신용", value = "PersonalCredit"),
                    @ExampleObject(name = "법인체크", value = "CorporateCheck"),
                    @ExampleObject(name = "법인신용", value = "CorporateCredit")
            }),
            @Parameter(name="dateType", description = "날짜별 검색", examples = {
                    @ExampleObject(name = "createdAt", value = "생성날짜기준"),
                    @ExampleObject(name = "modifiedAt", value = "수정일 기준"),
                    @ExampleObject(name = "deleteAt", value = "삭제날짜 기준")
            }), // 여기 날짜 뭐뭐있는지 설명
            @Parameter(name="sDate", description = "시작날짜"),
            @Parameter(name="dDate", description = "끝날짜"),
    })
    @GetMapping("/list")
    public JSONData cardList(@ModelAttribute CardSearch cardSearch) {

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
    @Operation(summary = "유저 카드 생성", method="POST", description = "유저 카드 생성")
    @ApiResponse(responseCode = "200")
    @Parameter(name="seq", description = "카드 번호")
    @PostMapping("/user/create")
    public JSONData createCard(@RequestBody List<Long> card) {

        List<UserCardEntity> cards = userCardUpdateService.update(card);
        return new JSONData(cards);
    }

    /**
     * user Card 단일 조회
     * @return
     */
    @Operation(summary = "유저 카드 단일 조회", method="GET", description = "유저 카드 단일 조회")
    @ApiResponse(responseCode = "200")
    @Parameter(name="seq", description = "유저 카드 번호")
    @GetMapping("/user/view/{seq}")
    public JSONData userCardInfo(@PathVariable("seq") Long seq) {
        UserCardEntity card = userCardInfoService.get(seq);
        return new JSONData(card);
    }


    /**
     * user card 목록 조회
     * @return
     */
    @Operation(summary = "유저 카드 목록 조회", method="GET", description = "data - 유저 카드 목록, pagination - 페이징 기초 데이터")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name="page", description = "페이지 번호", example = "1"),
            @Parameter(name="limit", description = "한페이지당 레코드 갯수", example = "20"),
            @Parameter(name="sopt", description = "검색옵션", example = "ALL"),
            @Parameter(name="skey", description = "검색키워드"),
            @Parameter(name="CardName", description = "카드이름 별 검색"),
            @Parameter(name = "BankName", description = "은행이름 별 검색", examples = {
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
            @Parameter(name="cardLimitMax", description = "최대한도"),
            @Parameter(name="cardLimitMin", description = "최소한도"),
            @Parameter(name="Category", description = "카테고리별 검색", examples = {
                    @ExampleObject(name = "SHOPPING", value = "SHOPPING"),
                    @ExampleObject(name = "LIFE", value = "LIFE"),
                    @ExampleObject(name = "TRAVEL", value = "TRAVEL"),
                    @ExampleObject(name = "LIVING", value = "LIVING")
            }), // 여기 날짜 뭐뭐있는지 설명
            @Parameter(name="CardType", description = "카드타입별 검색", examples = {
                    @ExampleObject(name = "개인체크", value = "PersonalCheck"),
                    @ExampleObject(name = "개인신용", value = "PersonalCredit"),
                    @ExampleObject(name = "법인체크", value = "CorporateCheck"),
                    @ExampleObject(name = "법인신용", value = "CorporateCredit")
            }),
            @Parameter(name="dateType", description = "날짜별 검색", examples = {
                    @ExampleObject(name = "createdAt", value = "생성날짜기준"),
                    @ExampleObject(name = "modifiedAt", value = "수정일 기준"),
                    @ExampleObject(name = "deleteAt", value = "삭제날짜 기준")
            }), // 여기 날짜 뭐뭐있는지 설명
            @Parameter(name="sDate", description = "시작날짜"),
            @Parameter(name="dDate", description = "끝날짜"),
    })
    @GetMapping("/user/list")
    public JSONData userCardList(@ModelAttribute RecommendCardSearch search) {
        ListData<UserCardEntity> cards = new ListData<>();

        String mode = search.getMode();
        if (search.getMode().equals("USER")) {
            cards = userCardInfoService.getMyList(search);
        } else if (search.getMode().equals("ADMIN") && memberUtil.isAdmin()) {
            cards = userCardInfoService.cardList(search);
        }

        return new JSONData(cards);
    }

    /**
     * 유저 카드 목록 삭제 - deleteAt Update
     * @return
     */
    @Operation(summary = "유저 카드 목록 삭제", method = "PATCH")
    @ApiResponse(responseCode = "200", description = "유저 카드 삭제. 실제로 DB 내에서는 지워지는게 아님.")
    @Parameter(name="seq", description = "유저 카드 번호")
    @PatchMapping("/user/deletes")
    public JSONData deleteUserCards(List<Long> seq) {
        List<UserCardEntity> cards = userCardDeleteService.deletes(seq, false, "remove");
        return new JSONData(cards);
    }
}























