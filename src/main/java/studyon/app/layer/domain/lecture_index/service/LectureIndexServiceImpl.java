package studyon.app.layer.domain.lecture_index.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.domain.file.repository.FileRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
import studyon.app.layer.domain.lecture_index.repository.LectureIndexRepository;
import studyon.app.layer.domain.lecture_video.LectureVideo;
import studyon.app.layer.domain.lecture_video.repository.LectureVideoRepository;

import java.util.List;
import java.util.Optional;

import static studyon.app.layer.base.utils.DTOMapper.toReadDTO;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LectureIndexServiceImpl implements LectureIndexService {

    private final FileRepository fileRepository;
    private final LectureIndexRepository lectureIndexRepository;
    private final LectureRepository lectureRepository;
    private final LectureVideoRepository lectureVideoRepository;
    private final FileManager fileManager;

    /**
     * 특정 강의의 목차 리스트 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<LectureIndexDTO.Read> readAllByLectureId(Long lectureId, Long teacherId) {
        validateLectureByTeacher(lectureId, teacherId);

        return lectureIndexRepository.findByLecture_LectureIdOrderByIndexNumberAsc(lectureId)
                .stream()
                .map(index -> LectureIndexDTO.Read.builder()
                        .lectureIndexId(index.getLectureIndexId())
                        .indexNumber(index.getIndexNumber())
                        .indexTitle(index.getIndexTitle())
                        .lectureId(lectureId)
                        // ✅ 여기서 대표 영상 파일명만 조회
                        .videoFileName(
                                lectureVideoRepository
                                        .findFirstByLectureIndex_LectureIndexIdOrderBySeqAsc(index.getLectureIndexId())
                                        .map(video -> video.getTitle()) // 또는 video.getVideoFile().getOriginalFileName()
                                        .orElse(null)
                        )
                        .build()
                )
                .toList();
    }


    /**
     * 강의 목차 하나 등록
     */
    @Override
    public void createIndex(Long lectureId, Long teacherId, LectureIndexDTO.Write dto) {
        Lecture lecture = validateLectureByTeacher(lectureId, teacherId);

        LectureIndex index = LectureIndex.builder()
                .indexTitle(dto.getIndexTitle())
                .indexNumber(dto.getIndexNumber())
                .lecture(lecture)
                .build();

        lectureIndexRepository.save(index);
    }

    /**
     * 강의 목차 일괄 수정
     */
    @Override
    @Transactional
    public void updateIndexes(Long lectureId, Long teacherId, List<LectureIndexDTO.Edit> dtos) {
        validateLectureByTeacher(lectureId, teacherId);

        for (LectureIndexDTO.Edit dto : dtos) {
            LectureIndex index = lectureIndexRepository.findById(dto.getLectureIndexId())
                    .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

            if (!index.getLecture().getLectureId().equals(lectureId)) {
                throw new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND);
            }
            log.info("📥 updateIndexes 호출됨 lectureId={}, teacherId={}, dtos={}", lectureId, teacherId, dtos);

            // ✅ 순서 및 제목 모두 갱신 (Dirty Checking 자동 반영)
            index.setIndexNumber(dto.getIndexNumber());
            index.setIndexTitle(dto.getIndexTitle());
        }
    }

    /**
     * 강의 목차 삭제
     */
    @Override
    public void deleteIndex(Long lectureIndexId, Long teacherId) {

        LectureIndex index = lectureIndexRepository
                .findById(lectureIndexId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        if (!index.getLecture().getTeacher().getTeacherId().equals(teacherId)) {
            throw new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND);
        }


        Long lectureId = index.getLecture().getLectureId();

        lectureIndexRepository.delete(index);
        //추가 video 갯수갱신 (KHS)

        long count = lectureVideoRepository.countByLectureIndex_Lecture_LectureId(lectureId);
        Lecture lecture = index.getLecture();
        lecture.setVideoCount(count);
        log.info("🎞 영상 개수 갱신 완료 → lectureId={}, count={}", lectureId, count);


    }

    @Override
    @Transactional(readOnly = true)
    public List<LectureIndexDTO.Read> readMemberAllByLectureId(Long lectureId) {

        //  강의에 연결된 모든 목차 조회
        return lectureIndexRepository.findByLecture_LectureIdOrderByIndexNumberAsc(lectureId)
                .stream()
                .map(index -> {
                    //  1. 각 목차의 첫 번째 영상 조회 (seq 오름차순)
                    Optional<LectureVideo> videoOpt =
                            lectureVideoRepository.findFirstByLectureIndex_LectureIndexIdOrderBySeqAsc(index.getLectureIndexId());

                    //  2. 영상 정보가 있다면 경로와 이름 추출
                    String videoFileName = videoOpt.map(LectureVideo::getTitle).orElse(null);
                    String videoFilePath =
                            videoOpt.map(video -> fileManager.getFullVideoUrl(video.getVideoUrl())).orElse(null);

                    //  3. DTO 생성
                    return LectureIndexDTO.Read.builder()
                            .lectureIndexId(index.getLectureIndexId())
                            .indexNumber(index.getIndexNumber())
                            .indexTitle(index.getIndexTitle())
                            .lectureId(lectureId)
                            .lectureTitle(index.getLecture().getTitle())
                            .videoFileName(videoFileName)   // ex) "1강 - AI 소개.mp4"
                            .videoFilePath(videoFilePath)   // ex) "lecture_video/a3b4c5d6e7.mp4"
                            .build();
                })
                .toList();
    }



    /**
     * 강의 ID와 강사 ID 일치 여부 검증
     */
    private Lecture validateLectureByTeacher(Long lectureId, Long teacherId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        if (!lecture.getTeacher().getTeacherId().equals(teacherId)) {
            throw new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND);
        }

        return lecture;
    }
}
