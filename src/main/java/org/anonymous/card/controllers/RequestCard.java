package org.anonymous.card.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;

@Data
public class RequestCard {

    @NotBlank
    private String cardName;

    private int annualFee; // 연회비 특성1

    @NotNull
    private CardType cardType; // 카드종류 -> 이넘으로 해야할듯? -> 1. 개인체크, 2. 개인신용 3. 법인체크, 4. 법인신용.. 특성2 -> 변할수도 있는 자료형

    @NotNull
    private Long limit; // 카드한도 특성3

    @NotNull
    private BankName bankName; // 은행 종류 이 친구도 이넘으로 해야할듯. 특성4 -> 변할수도 있는 자료형

    @NotNull
    private Category category; // 카테고리 SHOPPING, LIFE 등등.. -> 이넘으로 체크하자 특성5 -> 변할수도 있는 자료형

    @NotBlank
    private String cardDescription; // 카드설명
}
