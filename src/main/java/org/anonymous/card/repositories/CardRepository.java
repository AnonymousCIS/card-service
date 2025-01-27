package org.anonymous.card.repositories;

import org.anonymous.card.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CardRepository extends JpaRepository<CardEntity, Long>, QuerydslPredicateExecutor<CardEntity> {
}
