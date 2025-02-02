package org.anonymous.card.services;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.repositories.CardInfoRepository;
import org.anonymous.global.libs.Utils;
import org.anonymous.member.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class CardDeleteService {

    private final Utils utils;
    private final CardInfoService infoService;
    private final CardInfoRepository infoRepository;
    private final MemberUtil memberUtil;

}
