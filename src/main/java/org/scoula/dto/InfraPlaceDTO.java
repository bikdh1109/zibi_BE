package org.scoula.dto;

import lombok.Data;

@Data
public class InfraPlaceDTO {
    private String placeType;
    private Long placeIdx;
    private String placeName;
    private Double distance;
    private String roadAddressName;
}
