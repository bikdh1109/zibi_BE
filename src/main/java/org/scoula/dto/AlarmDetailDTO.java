package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.domain.AlarmType;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDetailDTO {
    private Long alarmIdx;
    private AlarmType alarmType;
    private String title;
    private LocalDate alarmDate;
    private LocalTime alarmTime;
    private String content;
    private String link;
    private boolean isRouting;
    private boolean isRead;
    private Integer usersIdx;
}
