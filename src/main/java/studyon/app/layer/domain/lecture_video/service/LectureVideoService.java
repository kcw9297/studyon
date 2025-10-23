package studyon.app.layer.domain.lecture_video.service;

import studyon.app.layer.domain.lecture_video.LectureVideoDTO;

import java.util.List;

public interface LectureVideoService {

    // ✅ 수강 중인 유저가 볼 수 있는 강의 영상 목록
    List<LectureVideoDTO.Read> getVideosForMemberLecture(Long memberId, Long lectureId);
}
