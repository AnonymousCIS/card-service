package org.anonymous.card.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.global.entities.BaseMemberEntity;

@Data
@Entity
/**
 * 추천 받은 카드 보유 기록.
 */
public class UserCardEntity extends BaseMemberEntity {

    @Id @GeneratedValue
    private Long seq;

    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardEntity card;
}