package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeSizeDTO {

    @JsonProperty("min_homesize")
    private int minHomesize;

    @JsonProperty("max_homesize")
    private int maxHomesize;
}