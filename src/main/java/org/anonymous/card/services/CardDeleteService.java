package org.anonymous.card.services;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.exceptions.CardNotFoundException;
import org.anonymous.card.repositories.CardRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class CardDeleteService {

    private final CardInfoService cardInfoService;
    private final CardRepository cardRepository;

    public CardEntity delete(Long seq, boolean mode, String removeMode) {
        CardEntity card = cardInfoService.getCardInfo(seq);

        if (card == null) {
            throw new CardNotFoundException();
        }

        card.setDeletedAt(LocalDateTime.now());
        if (mode) {
            cardRepository.saveAndFlush(card);
        }

        if (StringUtils.hasText(removeMode) && removeMode.equals("remove")) {
            cardRepository.delete(card);
            cardRepository.flush();
        }

        return card;
    }

    public List<CardEntity> deletes (List<Long> seqs, boolean mode, String removeMode) {
        List<CardEntity> cardEntities = new ArrayList<>();

        if (StringUtils.hasText(removeMode) && removeMode.equals("remove")) {
            for (Long seq : seqs) {
                CardEntity card = cardInfoService.getCardInfo(seq);
                cardEntities.add(card);
            }
            cardRepository.deleteAll(cardEntities);
            cardRepository.flush();
            return cardEntities;
        }
        for (Long seq : seqs) {
            CardEntity card = delete(seq, mode, removeMode);
            cardEntities.add(card);
        }
        if (StringUtils.hasText(removeMode) && removeMode.equals("delete")) {
            cardRepository.saveAllAndFlush(cardEntities);
        }
        return cardEntities;
    }

}
