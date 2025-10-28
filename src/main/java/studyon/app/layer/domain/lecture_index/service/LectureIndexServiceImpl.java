package studyon.app.layer.domain.lecture_index.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
import studyon.app.layer.domain.lecture_index.repository.LectureIndexRepository;
import studyon.app.layer.domain.lecture_video.repository.LectureVideoRepository;

import java.util.List;

import static studyon.app.layer.base.utils.DTOMapper.toReadDTO;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LectureIndexServiceImpl implements LectureIndexService {

    private final LectureIndexRepository lectureIndexRepository;
    private final LectureRepository lectureRepository;
    private final LectureVideoRepository lectureVideoRepository;

    /**
     * 특정 강의의 목차 리스트 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<LectureIndexDTO.Read> readAllByLectureId(Long lectureId, Long teacherId) {
        Lecture lecture = validateLectureByTeacher(lectureId, teacherId);

        return lectureIndexRepository.findByLecture_LectureIdOrderByIndexNumberAsc(lectureId)
                .stream()
                .map(index -> {
                    LectureIndexDTO.Read dto = toReadDTO(index);
                    return dto;
                })
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
    public void updateIndexes(Long lectureId, Long teacherId, List<LectureIndexDTO.Edit> dtos) {
        validateLectureByTeacher(lectureId, teacherId);

        for (LectureIndexDTO.Edit dto : dtos) {
            LectureIndex index = lectureIndexRepository.findById(dto.getLectureIndexId())
                    .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

            if (!index.getLecture().getLectureId().equals(lectureId)) {
                throw new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND);
            }

            index.update(dto.getIndexTitle(), dto.getIndexNumber());
        }
    }

    /**
     * 강의 목차 삭제
     */
    @Override
    public void deleteIndex(Long lectureIndexId, Long teacherId) {
        LectureIndex index = lectureIndexRepository.findById(lectureIndexId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        if (!index.getLecture().getTeacher().getTeacherId().equals(teacherId)) {
            throw new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND);
        }

        lectureIndexRepository.delete(index);
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
