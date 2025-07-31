package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePriceDTO {

    @JsonProperty("hope_min_price")
    private int hopeMinPrice;

    @JsonProperty("hope_max_price")
    private int hopeMaxPrice;
}