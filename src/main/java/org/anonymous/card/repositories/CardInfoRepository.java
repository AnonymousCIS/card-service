package org.anonymous.card.repositories;

import org.anonymous.card.entities.CardInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CardInfoRepository extends JpaRepository<CardInfo, Long>, QuerydslPredicateExecutor<CardInfo> {

    @EntityGraph(attributePaths = "config")
    Optional<CardInfo> findBySeq(Long seq);

    @EntityGraph(attributePaths = "config")
    List<CardInfo> findAllByCreatedBy(String email);
}
