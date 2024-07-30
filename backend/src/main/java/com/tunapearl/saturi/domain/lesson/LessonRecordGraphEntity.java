package com.tunapearl.saturi.domain.lesson;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "lesson_record_graph")
public class LessonRecordGraphEntity {

    @Id @GeneratedValue
    @Column(name = "lesson_record_graph_id")
    private Long lessonRecordGraphId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_result_id")
    private LessonResultEntity lessonResult;

    private Integer x;
    private Double y;
}