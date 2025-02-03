package org.anonymous.card.services;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.exceptions.CardDataNotFoundException;
import org.anonymous.card.controllers.CardSearch;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.global.exceptions.BadRequestException;
import org.anonymous.global.libs.Utils;
import org.anonymous.member.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;

@Lazy
@Service
@RequiredArgsConstructor
public class CardInfoService {

    private final Utils utils;

    private final CardRepository cardRepository;

    private final MemberUtil memberUtil;

    /**
     * 카드 단일 조회
     *
     * @param seq
     * @return
     */
    public CardEntity get(Long seq) {
        if (!memberUtil.isAdmin()){
            throw new BadRequestException();
        }
        CardEntity item = cardRepository.findBySeq(seq).orElseThrow(CardDataNotFoundException::new);
        return null;
    }

    /**
     * 카드 목록 조회
     *
     * @param search
     * @return
     */
    public ListData<Card> getList(CardSearch search) {

        return null;
    }
}
