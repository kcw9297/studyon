package studyon.app.layer.domain.lecture_video.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import studyon.app.layer.domain.lecture_video.LectureVideo;
import studyon.app.layer.domain.lecture_video.LectureVideoDTO;
import studyon.app.layer.domain.lecture_video.repository.LectureVideoRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureVideoServiceImpl implements LectureVideoService {

    private final LectureVideoRepository lectureVideoRepository;

    @Override
    public List<LectureVideoDTO.Read> getVideosForMemberLecture(Long memberId, Long lectureId) {
        // ✅ 추후 memberLecture 검증 붙일 예정
        log.info("🎬 [VIDEO LIST] memberId={}, lectureId={}", memberId, lectureId);

        List<LectureVideo> videos = lectureVideoRepository.findAllByLectureId(lectureId);

        return videos.stream()
                .map(v -> LectureVideoDTO.Read.builder()
                        .lectureVideoId(v.getLectureVideoId())
                        .title(v.getTitle())
                        .seq(v.getSeq())
                        .duration(v.getDuration())
                        .videoUrl(v.getVideoUrl())
                        .createdAt(v.getCreatedAt())
                        .updatedAt(v.getUpdatedAt())
                        .lectureIndexId(v.getLectureIndex().getLectureIndexId())
                        .build())
                .collect(Collectors.toList());
    }
}
