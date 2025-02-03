package org.anonymous.card.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.card.services.CardCreateService;
import org.anonymous.card.services.CardDeleteService;
import org.anonymous.card.services.CardInfoService;
import org.anonymous.card.services.TrainService;
import org.anonymous.global.libs.Utils;
import org.anonymous.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

import javax.smartcardio.Card;
import java.util.List;

@RestController
@RequiredArgsConstructor
/**
 * 일반 유저 & 관리자 공용
 */
public class CardController {

    private final Utils utils;
    private final CardInfoService infoService;
    private final CardDeleteService deleteService;
    private final TrainService trainService;
    private final CardRepository cardRepository;

    // 1. RecommendCard C - (목록, 업데이트처리지 수정이 아님) - PostMapping
    // R (단일, 목록 조회(Pagination)) - GetMapping

    // 2. Card Entity R - (단일, 목록 조회(Pagination)) - GetMapping


    /**
     * 3. UserCard C - (단일) - PostMapping // /user/save/{seq}
     *
     * @return
     */
    @Operation(summary = "회원 보유카드 추가", description = "추천받은 신규카드에서 원하는 카드 선택하여 추가")

    @PostMapping("/user/save/{seq}")
    public JSONData save() {
    // 카드엔티티에서 있는지 유무확인후 저장
    // 카드 seq 관계매핑
    // 데이터랑 회원 이메일(createdby)

            return null;
        }


        /**
         * 3. UserCard R - (단일, 목록 조회(Pagination) - GetMapping // /user/info
         * @param seq
         * @return
         */

        @Operation(summary = "회원 보유카드 단일 조회", description = "회원 보유중인 카드 단일 조회")

        @GetMapping("/user/view/{seq}") // 주소로 받는거
        public JSONData view (@PathVariable("seq") Long seq){

            Card data = infoService.get(seq);

            return new JSONData(data);
        }


        @Operation(summary = "회원 보유카드 목록 조회", description = "회원 보유중인 카드 목록 조회")

        @GetMapping("/user/list/{gid}")
        public JSONData list (@ModelAttribute CardSearch search){

            ListData<Card> data = infoService.getList(search);

            return new JSONData(data);
        }


        /**
         * 3. UserCard U - 유저 삭제(deletedAt을 현재 시간으로 하고 조회에서 제외) - PatchMapping // /user/delete
         *  // 목록 / 서치로 조건을 걸어서 가져올거
         * @return
         */

        @Operation(summary = "회원 보유카드 삭제", description = "회원 카드목록에서 삭제시 관리자모드에서는 데이터 남아있으나 회원쪽에서는 삭제 (deletedAt 삭제시점 시간으로 조회에서 제외)")

        @PatchMapping("/user/delete")
        public JSONData deletes (@RequestParam("seq") List < Long > seqs) {

            List<Card> data = deleteService.userDeletes(seqs);

            return new JSONData(data);
        }

    }
}