package com.tunapearl.saturi.controller;

import com.sun.source.tree.LiteralTree;
import com.tunapearl.saturi.domain.lesson.LessonCategoryEntity;
import com.tunapearl.saturi.domain.lesson.LessonEntity;
import com.tunapearl.saturi.domain.lesson.LessonGroupEntity;
import com.tunapearl.saturi.dto.admin.lesson.LessonGroupResponseDTO;
import com.tunapearl.saturi.dto.admin.lesson.LessonResponseDTO;
import com.tunapearl.saturi.dto.lesson.LessonGroupProgressByUserDTO;
import com.tunapearl.saturi.dto.lesson.LessonGroupProgressResponseDTO;
import com.tunapearl.saturi.dto.user.UserExpAndRankDTO;
import com.tunapearl.saturi.dto.user.UserInfoResponseDTO;
import com.tunapearl.saturi.exception.UnAuthorizedException;
import com.tunapearl.saturi.service.lesson.LessonService;
import com.tunapearl.saturi.service.user.UserService;
import com.tunapearl.saturi.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/learn")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LessonController {

    private final LessonService lessonService;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    /**
     * 모든 카테고리 조회
     */
    @GetMapping("/lesson-category")
    public ResponseEntity<List<LessonCategoryEntity>> getAllLessonCategory() {
        log.info("received request to get all lesson category");
        return ResponseEntity.ok(lessonService.findAllLessonCategory());
    }
    
    /**
     * 퍼즐 조회(지역+유형)
     * 레슨 그룹, 그룹안에 들어가있는 레슨들 정보도 같이 보냄
     */
    @GetMapping("/lesson-group")
    public ResponseEntity<List<LessonGroupResponseDTO>> getLessonGroupIdByLocationAndCategory(@RequestParam Long locationId,
                                                                                              @RequestParam Long categoryId) {
        log.info("received request to get lesson group id by location and category {}, {}", locationId, categoryId);
        List<LessonGroupEntity> lessonGroupByLocationAndCategory = lessonService.findLessonGroupByLocationAndCategory(locationId, categoryId);
        List<LessonGroupResponseDTO> result = lessonGroupByLocationAndCategory.stream()
                .map(g -> new LessonGroupResponseDTO(g)).toList();
        return ResponseEntity.ok(result);
    }

    /**
     * 레슨 개별 조회
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<LessonResponseDTO> getLesson(@PathVariable Long lessonId) {
        log.info("received request to find Lesson {}", lessonId);
        LessonEntity findLesson = lessonService.findById(lessonId);
        return ResponseEntity.ok(new LessonResponseDTO(findLesson.getLessonId(),
                findLesson.getLessonGroup().getLessonGroupId(), findLesson.getLessonGroup().getName(),
                findLesson.getSampleVoicePath(), findLesson.getScript(), findLesson.getLastUpdateDt()));
    }

    /**
     * 현재 지역과 유형에 맞는 퍼즐의 유저별 정보 조회
     * 진척도, 퍼즐별(진행률, 평균 정확도), 유저 정보(경험치, 순위)
     */
    @GetMapping("/lesson-group/progress")
    public ResponseEntity<LessonGroupProgressResponseDTO> getLessonGroupProgressByUser(@RequestHeader("Authorization") String authorization,
                                                                                       @RequestParam("locationId") Long locationId,
                                                                                       @RequestParam("categoryId") Long lessonCategoryId) throws UnAuthorizedException {
        Long userId = jwtUtil.getUserId(authorization);
        UserInfoResponseDTO userProfile = userService.getUserProfile(userId);
        Long userRank = userService.getUserRank(userId);
        UserExpAndRankDTO userInfo = new UserExpAndRankDTO(userProfile.getExp(), userRank);

        // 85~87 test 위함
        LessonGroupProgressByUserDTO lgpbuDTO = new LessonGroupProgressByUserDTO(1L, 0L, 0L);
        List<LessonGroupProgressByUserDTO> lgpbuDTOs = new ArrayList<>();
        lgpbuDTOs.add(lgpbuDTO);

        return ResponseEntity.ok(new LessonGroupProgressResponseDTO(0L, lgpbuDTOs, userInfo));
    }
}
