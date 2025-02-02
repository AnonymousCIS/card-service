package org.anonymous.card.repositories;

import org.anonymous.card.entities.Config;
import org.anonymous.card.entities.QConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ConfigRepository extends JpaRepository<Config, String>, QuerydslPredicateExecutor<Config> {

    default boolean exists(String bid) {

        QConfig config = QConfig.config;

        return exists(config.cid.eq(bid));
    }
}
