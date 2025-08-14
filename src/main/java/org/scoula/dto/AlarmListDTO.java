package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmListDTO {
    private Long alarmIdx;
    private String title;
    private LocalDate alarmDate;
    private LocalTime alarmTime;
    private String content;
    private String link;
    private boolean isRouting;
}
