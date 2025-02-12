package org.anonymous.card.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.global.entities.BaseMemberEntity;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
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