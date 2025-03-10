package org.anonymous.card.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.card.constants.BankName;
import org.anonymous.card.constants.CardType;
import org.anonymous.card.constants.Category;
import org.anonymous.global.entities.BaseEntity;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class CardEntity extends BaseEntity {

    @Id @GeneratedValue
    private Long seq;

    @Column(nullable = false)
    private String cardName; // 카드이름

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BankName bankName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Long limit;

    private int annualFee;

    @Lob
    @Column(nullable = false)
    private String cardDescription; // 카드설명

    private boolean done;

    private boolean isOpen;

    private int item1_annualFee; // 연회비 특성1

    private int item2_cardType; // 카드종류 -> 이넘으로 해야할듯? -> 1. 개인체크, 2. 개인신용 3. 법인체크, 4. 법인신용.. 특성2 -> 변할수도 있는 자료형

    @Column(nullable = false)
    private Long item3_limit; // 카드한도 특성3

    private int item4_bankName; // 은행 종류 이 친구도 이넘으로 해야할듯. 특성4 -> 변할수도 있는 자료형

    private int item5_category; // 카테고리 SHOPPING, LIFE 등등.. -> 이넘으로 체크하자 특성5 -> 변할수도 있는 자료형

    public String getCardTypeStr() {
        return cardType == null ? "" : cardType.getTitle();
    }

    public String getBankNameStr() {
        return bankName == null ? "" : bankName.getTitle();
    }

    public String getCategoryStr() {
        return category == null ? "" : category.getTitle();
    }
}
