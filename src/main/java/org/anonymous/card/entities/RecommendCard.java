package org.anonymous.card.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class RecommendCard {

    // 회원별 추천 카드 Entity 혹은 커맨드 객체가 필요한지
    // 단순 조회만 하므로 아무 Data Class도 필요 없는지
    // 만약 Entity가 필요할 경우 추천 서비스 사용시마다 DROP TABLE하는 여부

    // 카드 추천 서비스시 createdAt 가장 최신것 view
    // 나의 전체 추천 목록 조회시 전체 view

    @Id
    @GeneratedValue
    private Long seq;

    private String name; //회원명

    private String email; //회원 이메일

    private List<CardInfo> recommendCardInfos;
}
