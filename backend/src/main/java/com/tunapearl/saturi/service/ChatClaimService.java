package com.tunapearl.saturi.service;


import com.tunapearl.saturi.domain.game.ChatClaimEntity;
import com.tunapearl.saturi.domain.game.GameLogEntity;
import com.tunapearl.saturi.domain.quiz.QuizEntity;
import com.tunapearl.saturi.domain.user.UserEntity;
import com.tunapearl.saturi.dto.admin.claim.ClaimDeleteRequestDto;
import com.tunapearl.saturi.dto.admin.claim.ClaimReadRequestDto;
import com.tunapearl.saturi.dto.admin.claim.ClaimReadResponseDto;
import com.tunapearl.saturi.repository.ChatClaimRepository;
import com.tunapearl.saturi.repository.game.GameLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatClaimService {

    private final ChatClaimRepository chatClaimRepository;
    private final GameLogRepository gameLogRepository;

    /*
    * 유저 채팅 신고 하기
    */
    public void saveClaim(Long gameLogId){
        GameLogEntity gameLog = gameLogRepository.findById(gameLogId).orElseThrow(()
                -> new RuntimeException(String.format("Not found game log for %d", gameLogId)));
        ChatClaimEntity chatClaimEntity = ChatClaimEntity.createChatClaim(gameLog);
        chatClaimRepository.save(chatClaimEntity);
    }


    /*
    * 유저 채팅 신고 전체 조회
    */
    public List<ClaimReadResponseDto> findAll(ClaimReadRequestDto request) {
        List<ChatClaimEntity> list = chatClaimRepository.findAll(request);
        return list.stream().map(this::convertReadDtoToEntity).collect(Collectors.toList());
    }

    /*
    * 유저 채팅 신고 삭제(DB에 남기고 봤다는 표시만 함)
    */
    public void removeClaim(Long chatClaimId) {
        chatClaimRepository.removeById(chatClaimId);
    }

    /*
    * 유저 채팅 신고 처리
    */
    public void updateClaim(Long chatClaimId) {
        ChatClaimEntity entity = chatClaimRepository.findById(chatClaimId)
                .orElseThrow(() -> new RuntimeException("Invalid chat claim id: " + chatClaimId));
        entity.check();
    }


    private ClaimReadResponseDto convertReadDtoToEntity(ChatClaimEntity chatClaimEntity) {
        GameLogEntity gameLogEntity = chatClaimEntity.getGameLog();
        UserEntity user = gameLogEntity.getUser();
        QuizEntity quiz = gameLogEntity.getQuiz();
        return ClaimReadResponseDto.builder()
                .chatClaimId(chatClaimEntity.getChatClaimId())
                .gameLogId(gameLogEntity.getGameLogId())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .roomId(gameLogEntity.getRoom().getRoomId())
                .quizId(quiz.getQuizId())
                .question(quiz.getQuestion())
                .chatting(gameLogEntity.getChatting())
                .chattingDt(gameLogEntity.getChattingDt())
                .claimedDt(chatClaimEntity.getClaimedDt())
                .isChecked(chatClaimEntity.isChecked())
                .checkedDt(chatClaimEntity.getCheckedDt())
                .build();
    }
}
