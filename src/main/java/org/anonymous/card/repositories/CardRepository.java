package org.anonymous.card.repositories;

import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.QCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long>, QuerydslPredicateExecutor<CardEntity> {

    default boolean exists(String cardName) {

        QCardEntity card = QCardEntity.cardEntity;

        return exists(card.cardName.eq(cardName));
    }

    Optional<CardEntity> findByCardName(String cardName);
}
