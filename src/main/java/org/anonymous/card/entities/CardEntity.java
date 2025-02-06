package org.anonymous.card.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;
import org.anonymous.global.entities.BaseEntity;

@Data
@Entity
public class CardEntity extends BaseEntity {

    @Id @GeneratedValue
    private Long seq;

    private String cardName; // 카드이름

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private BankName bankName;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Long limit;

    private int annualFee;

    @Lob
    private String cardDescription; // 카드설명

    private boolean done;

    private boolean isOpen;

    private int item1_annualFee; // 연회비 특성1

    private int item2_cardType; // 카드종류 -> 이넘으로 해야할듯? -> 1. 개인체크, 2. 개인신용 3. 법인체크, 4. 법인신용.. 특성2 -> 변할수도 있는 자료형

    private Long item3_limit; // 카드한도 특성3

    private int item4_bankName; // 은행 종류 이 친구도 이넘으로 해야할듯. 특성4 -> 변할수도 있는 자료형

    private int item5_category; // 카테고리 SHOPPING, LIFE 등등.. -> 이넘으로 체크하자 특성5 -> 변할수도 있는 자료형
}
