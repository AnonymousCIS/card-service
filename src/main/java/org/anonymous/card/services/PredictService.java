package org.anonymous.card.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.anonymous.card.entities.CardEntity;
import org.anonymous.card.entities.RecommendCard;
import org.anonymous.card.repositories.CardRepository;
import org.anonymous.card.repositories.RecommendCardRepository;
import org.anonymous.member.MemberUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Lazy
@Service
@Profile("ml")
@RequiredArgsConstructor
public class PredictService {

    @Value("${python.run.path}")
    private String runPath;

    @Value("${python.script.path}")
    private String scriptPath;

    private final ObjectMapper om;

    private final RecommendCardRepository recommendCardRepository;
    private final CardInfoService cardInfoService;
    private final MemberUtil memberUtil;

    public List<Long> predict(List<Integer> items) {
        try {
            String data = om.writeValueAsString(items);

            ProcessBuilder builder = new ProcessBuilder(runPath, scriptPath + "/predict_KNeightbors.py", data);
            Process process = builder.start();

            int exitCode = process.waitFor();

            InputStream in = process.getInputStream();

            InputStream err = process.getErrorStream();
            String errorString = new String(err.readAllBytes(), StandardCharsets.UTF_8);
            if (!errorString.isEmpty()) {
                System.err.println("Python 오류 메시지: " + errorString);
            }

            List<Long> results = om.readValue(in.readAllBytes(), new TypeReference<>() {});
            List<RecommendCard> recommendCards = new ArrayList<>();
            for (Long result : results) {
                RecommendCard recommendCard = new RecommendCard();
                recommendCard.setEmail(memberUtil.getMember().getEmail());
                CardEntity card = cardInfoService.getCardInfo(result);
                recommendCard.setCard(card);
                recommendCards.add(recommendCard);
            }
            recommendCardRepository.saveAllAndFlush(recommendCards);
            return om.readValue(in.readAllBytes(), new TypeReference<>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }
}
