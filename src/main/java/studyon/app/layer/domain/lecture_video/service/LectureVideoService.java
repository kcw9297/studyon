package studyon.app.layer.domain.lecture_video.service;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.layer.domain.lecture_video.LectureVideoDTO;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.List;

public interface LectureVideoService {

    // ✅ 수강 중인 유저가 볼 수 있는 강의 영상 목록
    List<LectureVideoDTO.Read> getVideosForMemberLecture(Long memberId, Long lectureId);
    void uploadVideo(Long indexId, MultipartFile file);
    List<LectureVideoDTO.Read> getVideosByIndexId(Long indexId);

    void verifyAccess(MemberProfile profile, Long lectureId);
}
