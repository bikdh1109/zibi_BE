package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.PredictRequestDTO;
import org.scoula.dto.PredictResponseDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Log4j2
public class PythonApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String PYTHON_API_URL = "http://13.209.161.22/predict";

    public double requestPrediction(PredictRequestDTO input) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PredictRequestDTO> request = new HttpEntity<>(input, headers);

        ResponseEntity<PredictResponseDTO> response = restTemplate.postForEntity(
                PYTHON_API_URL,
                request,
                PredictResponseDTO.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getWinProbability();
        } else {
            throw new RuntimeException("FastAPI 예측 요청 실패: " + response.getStatusCode());
        }
    }
}