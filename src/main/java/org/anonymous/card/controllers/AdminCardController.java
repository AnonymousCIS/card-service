package org.anonymous.card.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.anonymous.global.exceptions.BadRequestException;
import org.anonymous.global.libs.Utils;
import org.anonymous.global.rests.JSONData;
import org.apache.http.client.config.RequestConfig;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin Card API", description = "관리자 전용 Card 기능")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCardController {

    private final Utils utils;

    /**
     * 카드 설정 등록, 수정 처리
     * @return
     */
    @Operation(summary = "카드 단일 등록 & 수정 처리", description = "신규 카드 설정을 등록하거나 혹은 기존 카드 설정을 수정합니다.")

    @PostMapping("/config/save")
    public JSONData save(@Valid @RequestBody RequestConfig form, Errors errors) {



        if (errors.hasErrors()) {

            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        return null;
    }

    /**
     * 카드 설정 목록
     * @return
     */
    @Operation(summary = "카드 목록 조회", description = "카드 목록을 검색해 조회합니다.")
    @Parameters({
            @Parameter(name = "search", description = "카드 단일 & 목록 조회용"),
            @Parameter(name = "cid", description = "카드 ID", required = true)
    })


    /**
     * 카드 단일 | 목록 일괄 수정
     * @return
     */
    @PatchMapping("/update")
    public JSONData update() {

        return null;
    }

    /**
     * 카드 단일 | 목록 삭제 처리
     * @param cids
     * @return
     */
    @DeleteMapping("/deletes")
    public JSONData Deletes(@RequestParam("cid") List<String> cids) {

        return null;
    }

}
