package org.anonymous.card.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.QCardEntity;
import org.anonymous.card.entities.TrainCardLog;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.card.repositories.TrainLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Lazy
@Service
@Profile("ml")
@RequiredArgsConstructor
public class TrainService {

    @Value("${python.run.path}")
    private String runPath;

    @Value("${python.script.path}")
    private String scriptPath;

    private final CardRepository repository;

    private final TrainLogRepository trainLogRepository;

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
    public String train() {
        try {
            TrainCardLog trainLog = new TrainCardLog();
            trainLog.setDone(true);
            log.info("훈련 시작");
            ProcessBuilder builder = new ProcessBuilder(runPath, scriptPath + "/train.py");
            Process process = builder.start();
            int code = process.waitFor();

            InputStream err = process.getErrorStream();
            String errorString = new String(err.readAllBytes(), StandardCharsets.UTF_8);
            if (!errorString.isEmpty()) {
                System.err.println("Python 오류 메시지: " + errorString);
                trainLog.setDone(false);
            }

            log.info("훈련 완료: {}", code);

            trainLog.setCount(repository.count());


            // 훈련 데이터 완료 처리
            QCardEntity cardEntity = QCardEntity.cardEntity;
            List<CardEntity> items = (List<CardEntity>)repository.findAll(cardEntity.done.eq(false));
            items.forEach(item -> item.setDone(true));
            repository.saveAllAndFlush(items);
            trainLogRepository.saveAndFlush(trainLog);

            if (code == 0) return "훈련 완료";
            else return errorString;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "훈련 실패";
    }
}
