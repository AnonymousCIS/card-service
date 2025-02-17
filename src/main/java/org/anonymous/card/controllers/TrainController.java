package org.anonymous.card.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.TrainCardLog;
import org.anonymous.card.exceptions.TrainLogNotFoundException;
import org.anonymous.card.services.PredictService;
import org.anonymous.card.services.TrainLogInfoService;
import org.anonymous.card.services.TrainService;
import org.anonymous.global.paging.CommonSearch;
import org.anonymous.global.paging.ListData;
import org.anonymous.global.rests.JSONData;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Tag(name = "Train", description = "카드 머신러닝용 API")
@Profile("ml")
@RestController
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final PredictService predictService;
    private final TrainLogInfoService trainLogInfoService;

    @Operation(summary = "train 조회", method="GET", description = "train DB 조회")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name="mode", description = "string 문자열"),
    })
    @GetMapping("/train/{mode}")
    public List<CardEntity> train(@PathVariable("mode") String mode) {
        return trainService.getList(mode.equals("all"));
    }


    @Operation(summary = "예측", method="GET", description = "카드의 최근접 이웃 회귀 5개의 데이터 return. only 특성으로 수치화를 해서 넘겨줘야한다.")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name="item1_annualFee", description = "연회비"),
            @Parameter(name="item2_cardType", description = "카드 종류"),
            @Parameter(name="item3_limit", description = "카드한도"),
            @Parameter(name="item4_bankName", description = "은행종류"),
            @Parameter(name="item5_category", description = "카테고리"),
    })
    @GetMapping("/predict")
    public JSONData predict(@RequestParam("data") String data) {
        List<Integer> items = Arrays.stream(data.split("_")).map(Integer::valueOf).toList();
        List<CardEntity> cardEntities = predictService.predict(items);
        return new JSONData(cardEntities);
    }

    @Operation(summary = "학습 로그 검색", method="GET", description = "학습한 기록을 검색한다.")
    @ApiResponse(responseCode = "200")
    @Parameter(name="seq", description = "검색번호")
    @GetMapping("/train/log")
    public JSONData logGet(@RequestParam("seq") Long seq) {
        TrainCardLog trainLog = trainLogInfoService.get(seq);
        if (trainLog == null) {
            throw new TrainLogNotFoundException();
        }

        return new JSONData(trainLog);
    }

    @Operation(summary = "학습 로그 검색", method="GET", description = "학습한 기록을 검색하며, List로 반환해준다.")
    @ApiResponse(responseCode = "200")
    @GetMapping("/train/logs")
    public JSONData logGetList(@ModelAttribute CommonSearch search) {
        ListData<TrainCardLog> trainLog = trainLogInfoService.getList(search);
        if (trainLog == null) {
            throw new TrainLogNotFoundException();
        }

        return new JSONData(trainLog);
    }
}