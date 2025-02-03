package org.anonymous.card.services.usercard;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.entities.UserCardEntity;
import org.anonymous.card.exceptions.CardNotFoundException;
import org.anonymous.card.repositories.UserCardEntityRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class UserCardDeleteService {
    private final UserCardInfoService userCardInfoService;
    private final UserCardEntityRepository entityRepository;


    public UserCardEntity delete(Long seq, boolean mode, String removeMode) {
        UserCardEntity card = userCardInfoService.get(seq);

        if (card == null) {
            throw new CardNotFoundException();
        }

        card.setDeletedAt(LocalDateTime.now());
        if (mode) {
            entityRepository.saveAndFlush(card);
        }

        if (StringUtils.hasText(removeMode) && removeMode.equals("remove")) {
            entityRepository.delete(card);
            entityRepository.flush();
        }

        return card;
    }

    public List<UserCardEntity> deletes (List<Long> seqs, boolean mode, String removeMode) {
        List<UserCardEntity> cardEntities = new ArrayList<>();

        if (StringUtils.hasText(removeMode) && removeMode.equals("remove")) {
            for (Long seq : seqs) {
                UserCardEntity card = userCardInfoService.get(seq);
                cardEntities.add(card);
            }
            entityRepository.deleteAll(cardEntities);
            entityRepository.flush();
            return cardEntities;
        }
        for (Long seq : seqs) {
            UserCardEntity card = delete(seq, mode, removeMode);
            cardEntities.add(card);
        }
        if (StringUtils.hasText(removeMode) && removeMode.equals("delete")) {
            entityRepository.saveAllAndFlush(cardEntities);
        }
        return cardEntities;
    }
}
