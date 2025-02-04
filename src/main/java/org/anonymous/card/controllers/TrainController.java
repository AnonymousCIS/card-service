package org.anonymous.card.controllers;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.services.PredictService;
import org.anonymous.card.services.TrainService;
import org.anonymous.global.rests.JSONData;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@Profile("ml")
@RestController
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final PredictService predictService;

    @GetMapping("/train/{mode}")
    public List<CardEntity> train(@PathVariable("mode") String mode) {
        return trainService.getList(mode.equals("all"));
    }

    @GetMapping("/predict")
    public JSONData predict(@RequestParam("data") String data) {
        List<Integer> items = Arrays.stream(data.split("_")).map(Integer::valueOf).toList();
        List<CardEntity> cardEntities = predictService.predict(items);
        return new JSONData(cardEntities);
    }
}