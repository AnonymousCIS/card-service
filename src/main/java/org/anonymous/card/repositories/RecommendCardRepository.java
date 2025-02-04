package org.anonymous.card.repositories;

import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.entities.UserCardEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RecommendCardRepository extends JpaRepository<RecommendCard, Long> , QuerydslPredicateExecutor<RecommendCard> {

    @EntityGraph(attributePaths = "card")
    Optional<RecommendCard> findBySeq(Long seq);
}
