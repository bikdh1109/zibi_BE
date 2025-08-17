package org.scoula.dto;

import lombok.Data;

@Data
public class AlarmMessageRequest {
    private Integer userIdx;
    private String title;
    private String body;
}
