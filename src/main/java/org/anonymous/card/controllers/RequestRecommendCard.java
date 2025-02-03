package org.anonymous.card.controllers;

import lombok.Data;

@Data
public class RequestRecommendCard {
    
    private String email; // 추천 받았던 사람의 이메일
    
    private Long seq; // 카드 entity seq
}
