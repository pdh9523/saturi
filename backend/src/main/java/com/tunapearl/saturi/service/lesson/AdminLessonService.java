package com.tunapearl.saturi.service.lesson;

import com.tunapearl.saturi.domain.LocationEntity;
import com.tunapearl.saturi.domain.lesson.LessonCategoryEntity;
import com.tunapearl.saturi.domain.lesson.LessonEntity;
import com.tunapearl.saturi.domain.lesson.LessonGroupEntity;
import com.tunapearl.saturi.repository.lesson.AdminLessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminLessonService {

    private final AdminLessonRepository adminLessonRepository;

    @Transactional
    public LessonCategoryEntity createLessonCategory(String name) {
        LessonCategoryEntity lessonCategory = new LessonCategoryEntity();
        lessonCategory.setName(name);
        Long findLessonCategoryId = adminLessonRepository.saveLessonCategory(lessonCategory);
        return lessonCategory;
    }

    @Transactional
    public Long createLessonGroup(LocationEntity location, LessonCategoryEntity lessonCategory, String name) {
        //TODO 등록된 레슨 그룹이 이미 9개면 익셉션
        LessonGroupEntity lessonGroup = new LessonGroupEntity();
        lessonGroup.setLocation(location);
        lessonGroup.setLessonCategory(lessonCategory);
        lessonGroup.setName(name);
        return adminLessonRepository.saveLessonGroup(lessonGroup);
    }

    @Transactional
    public Long createLesson(LessonGroupEntity lessonGroup, String script, String filePath) {
        //TODO 등록된 레슨이 이미 5개면 익셉션
        LessonEntity lesson = new LessonEntity();
        lesson.setLessonGroup(lessonGroup);
        lesson.setScript(script);
        lesson.setSampleVoicePath(filePath);
        lesson.setLastUpdateDt(LocalDateTime.now());
        return adminLessonRepository.saveLesson(lesson);
    }

    public List<LessonEntity> findAll() {
        return adminLessonRepository.findAll().orElse(null);
    }

    public List<LessonEntity> findByLocationAndLessonCategory(Long lessonGroupId, Long locationId, Long lessonCategoryId) {
        return adminLessonRepository.findByLocationAndLessonCategory(lessonGroupId, locationId, lessonCategoryId).orElse(null);
    }

    public LessonEntity findById(Long lessonId) {
        return adminLessonRepository.findById(lessonId).orElse(null);
    }
}
