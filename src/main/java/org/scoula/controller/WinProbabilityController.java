package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import org.scoula.dto.PredictRequestDTO;
import org.scoula.service.PythonApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WinProbabilityController {
    private final PythonApiService pythonApiService;

    @PostMapping("/predict/from-python")
    public ResponseEntity<Double> getPrediction(@RequestBody PredictRequestDTO request) {
        double result = pythonApiService.requestPrediction(request);
        return ResponseEntity.ok(result);
    }
}