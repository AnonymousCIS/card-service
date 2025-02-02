package org.anonymous.card.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    public List<Integer> predict(List<Integer> items) {
        try {
            String data = om.writeValueAsString(items);

            System.out.println("예측시작");

            ProcessBuilder builder = new ProcessBuilder(runPath, scriptPath + "/predict_KNeightbors.py", data);
            Process process = builder.start();

            int exitCode = process.waitFor();

            System.out.println("예측종료 : " + exitCode);

            InputStream in = process.getInputStream();

            InputStream err = process.getErrorStream();
            String errorString = new String(err.readAllBytes(), StandardCharsets.UTF_8);
            if (!errorString.isEmpty()) {
                System.err.println("Python 오류 메시지: " + errorString);
            }

            return om.readValue(in.readAllBytes(), new TypeReference<>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }
}
