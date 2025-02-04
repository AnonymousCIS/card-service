package org.anonymous.card.repositories;

import org.anonymous.card.entities.UserCardEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserCardEntityRepository extends JpaRepository<UserCardEntity, Long>, QuerydslPredicateExecutor<UserCardEntity> {
    @EntityGraph(attributePaths = "card")
    Optional<UserCardEntity> findBySeq(Long seq);
}
