package org.anonymous.card.repositories;

import org.anonymous.card.entities.UserCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserCardEntityRepository extends JpaRepository<UserCardEntity, Long>, QuerydslPredicateExecutor<UserCardEntity> {
}
