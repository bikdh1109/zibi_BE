package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PredictResponseDTO {
    private double winProbability;
}
