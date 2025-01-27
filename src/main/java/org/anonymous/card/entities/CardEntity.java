package org.anonymous.card.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;

@Data
@Entity
public class CardEntity {

    @Id @GeneratedValue
    private Long seq;

    private String cardName; // 카드이름

    private int annualFee; // 연회비 특성1

    private int cardType; // 카드종류 -> 이넘으로 해야할듯? -> 1. 개인체크, 2. 개인신용 3. 법인체크, 4. 법인신용.. 특성2

    private Long limit; // 카드한도 특성3

    private String backName; // 은행 종류 이 친구도 이넘으로 해야할듯. 특성4

    private int category; // 카테고리 SHOPPING, LIFE 등등.. -> 이넘으로 체크하자 특성5

    @Lob
    private String cardDescription; // 카드설명

    private int target; // 정답데이터....

    private boolean done;
}
