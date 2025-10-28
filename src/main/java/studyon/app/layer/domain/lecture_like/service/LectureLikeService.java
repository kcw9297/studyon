package studyon.app.layer.domain.lecture_like.service;

import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.lecture_like.LectureLikeDTO;

import java.util.*;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-28) : phj 최초 작성
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.0
 * @author phj
 */

public interface LectureLikeService {
    // 좋아요 등록
    void addLike(LectureLikeDTO.Write dto);

    // 삭제
    void removeLike(LectureLikeDTO.Delete dto);

    // 상태 조회
    boolean isLiked(Long memberId, Long lectureId);

    // 좋아요 카운트
    long countByLectureId(Long lectureId);

    // 목록 조회
    List<LectureLike> getLikesByMember(Long memberId);
}
