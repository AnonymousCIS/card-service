package org.anonymous.card.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.global.entities.BaseEntity;

@Data
@Entity
/**
 * 추천 받은 카드 로그 기록.
 */
public class RecommendCard extends BaseEntity {

    @Id @GeneratedValue
    private Long seq;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardEntity card;
}
