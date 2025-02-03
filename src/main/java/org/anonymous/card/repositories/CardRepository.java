package org.anonymous.card.repositories;

import org.anonymous.card.entities.CardEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long>, QuerydslPredicateExecutor<CardEntity> {
    @EntityGraph(attributePaths = "config")
    Optional<CardEntity> findBySeq(Long seq);

}
