package org.anonymous.card.services.usercard;

import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.UserCardEntity;
import org.anonymous.card.exceptions.CardNotFoundException;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.card.repositories.UserCardEntityRepository;
import org.anonymous.card.services.CardInfoService;
import org.anonymous.member.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class UserCardUpdateService {

    private final UserCardEntityRepository entityRepository;
    private final MemberUtil memberUtil;
    private final CardRepository cardRepository;
    private final CardInfoService cardInfoService;

    public List<UserCardEntity> update(List<Long> seqs) {

        List<UserCardEntity> entities = new ArrayList<>();
        for (Long seq : seqs) {
            UserCardEntity userCard = new UserCardEntity();
            CardEntity card = cardInfoService.getCardInfo(seq);
            userCard.setCard(card);
            userCard.setEmail(memberUtil.getMember().getEmail());
            entities.add(userCard);
        }
        addInfo(false, null, entities);
        return entities;
    }

    public UserCardEntity update(Long seq) {

        CardEntity card = cardInfoService.getCardInfo(seq);

        UserCardEntity userCard = new UserCardEntity();

        userCard.setCard(card);
        userCard.setEmail(memberUtil.getMember().getEmail());

        addInfo(true, userCard, null);

        return userCard;
    }

    private void addInfo(boolean mode, UserCardEntity userCard, List<UserCardEntity> cards) {
        if (mode) {
            entityRepository.saveAndFlush(userCard);
        } else {
            entityRepository.saveAllAndFlush(cards);
        }
    }
}
