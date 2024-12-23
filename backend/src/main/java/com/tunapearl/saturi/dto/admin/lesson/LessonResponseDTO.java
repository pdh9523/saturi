package com.tunapearl.saturi.dto.admin.lesson;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LessonResponseDTO {
    private Long lessonId;
    private Long lessonGroupId;
    private String lessonGroupName;
    private String sampleVoicePath;
    private String sampleVoiceName;
    private String script;
    private String sampleVoicePitchX;
    private String sampleVoicePitchY;
    private LocalDateTime lastUpdateDt;
}
