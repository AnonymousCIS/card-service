package org.anonymous.card.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
/**
 * 일반 유저 & 관리자 공용
 */
public class CardController {

    // 1. RecommendCard C - (목록, 업데이트처리지 수정이 아님) - PostMapping
    // R (단일, 목록 조회(Pagination)) - GetMapping

    // 2. Card Entity R - (단일, 목록 조회(Pagination)) - GetMapping

    // 3. UserCard C - (단일) - PostMapping
    // R - (단일, 목록 조회(Pagination) - GetMapping
    // U - 유저 삭제(deletedAt을 현재 시간으로 하고 조회에서 제외) - PatchMapping
}