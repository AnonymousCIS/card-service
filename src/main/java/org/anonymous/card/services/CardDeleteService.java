package org.anonymous.card.services;


import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.UserCardEntity;
import org.anonymous.card.exceptions.CardDataNotFoundException;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.card.repositories.UserCardEntityRepository;
import org.anonymous.global.libs.Utils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Lazy
@Service
@RequiredArgsConstructor
public class CardDeleteService {

    private final Utils utils;
    private final CardInfoService infoService;
    private final UserCardEntityRepository userCardEntityRepository;

    /**
     * 카드 삭제 - DB 삭제안함
     * 유저 삭제(deletedAt을 현재 시간으로 하고 조회에서 제외)
     * @param seq
     * @return
     */
    public UserCardEntity userDelete(Long seq) {
        UserCardEntity item = infoService.get(seq);

        if (item == null) throw new CardDataNotFoundException();

        item.setDeletedAt(LocalDateTime.now());

        UserCardEntityRepository.saveAndFlush(item);

        return item;
    }

}
