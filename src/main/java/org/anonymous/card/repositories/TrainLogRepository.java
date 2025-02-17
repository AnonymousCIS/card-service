package org.anonymous.card.repositories;

import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.entities.TrainLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TrainLogRepository extends JpaRepository<TrainLog, Long>, QuerydslPredicateExecutor<TrainLog> {


}
