package org.anonymous.card.controllers;

import lombok.Data;
import org.anonymous.card.constants.Category;
import org.anonymous.global.paging.CommonSearch;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class CardSearch extends CommonSearch {
    private List<String> cardName;
    private List<String> bankName;
    private int cardLimitMax = 0;
    private int cardLimitMin = 0;
    private List<Integer> categories;
    private List<Integer> cardTypes;
    private String dateType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eDate;
}
