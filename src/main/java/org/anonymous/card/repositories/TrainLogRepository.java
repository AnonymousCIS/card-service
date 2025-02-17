package org.anonymous.card.repositories;

import org.anonymous.card.entities.TrainCardLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TrainLogRepository extends JpaRepository<TrainCardLog, Long>, QuerydslPredicateExecutor<TrainCardLog> {


}
