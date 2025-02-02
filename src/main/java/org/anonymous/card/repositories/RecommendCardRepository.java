package org.anonymous.card.repositories;

import org.anonymous.card.entities.RecommendCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RecommendCardRepository extends JpaRepository<RecommendCard, Long> , QuerydslPredicateExecutor<RecommendCard> {
}
