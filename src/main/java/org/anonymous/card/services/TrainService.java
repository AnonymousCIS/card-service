package org.anonymous.card.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.QCardEntity;
import org.anonymous.card.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Lazy
@Service
@Profile("ml")
@RequiredArgsConstructor
public class TrainService {

    @Value("${python.path}")
    private String runPath;

    @Value("${python.script}")
    private String scriptPath;

    private final CardRepository repository;

    /**
     * 훈련 데이터 조회
     *
     * @param isAll
     * @return
     */
    public List<CardEntity> getList(boolean isAll) {

        if (isAll) {
            return repository.findAll();
        } else {
            QCardEntity cardEntity = QCardEntity.cardEntity;
            return (List<CardEntity>) repository.findAll(cardEntity.done.eq(false));
        }
    }

    // 매일 자정에 훈련 진행
    @Scheduled(cron="0 0 0 * * *")
    public void train() {
        try {
            log.info("훈련 시작");
            ProcessBuilder builder = new ProcessBuilder(runPath, scriptPath + "partial.py");
            Process process = builder.start();
            int code = process.waitFor();
            log.info("훈련 완료: {}", code);

            // 훈련 데이터 완료 처리
            QCardEntity cardEntity = QCardEntity.cardEntity;
            List<CardEntity> items = (List<CardEntity>)repository.findAll(cardEntity.done.eq(false));
            items.forEach(item -> item.setDone(true));
            repository.saveAllAndFlush(items);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
