package org.anonymous.card.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
/**
 * 관리자 권한쪽
 */
public class AdminCardController {

    // 1. RecommendCard D - (단일, 목록 일괄 처리) - DeleteMapping

    // 2. Card Entity C - (단일) - PostMapping
    // U - (단일, 목록 일괄 처리) - PatchMapping
    // D - (단일, 목록 일괄 처리) - DeleteMapping

    // 3. UserCard D - (단일, 목록 일괄 처리) - DeleteMapping
}