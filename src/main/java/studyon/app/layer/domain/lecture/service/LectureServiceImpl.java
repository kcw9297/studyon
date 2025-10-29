package studyon.app.layer.domain.lecture.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.*;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.lecture_index.repository.LectureIndexRepository;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;
import studyon.app.layer.domain.lecture_video.repository.LectureVideoRepository;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.*;
import java.util.stream.Collectors;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-27) : phj03 : 리뷰 퍼센트 계산 - 강의페이지 추가
 *  ▶ ver 1.2 (2025-10-29) : kcw97 : 강의 최근 검색 목록, 강의 검색기능 세분화
 */

/**
 * 강의 서비스 인터페이스 구현체
 * @version 1.2
 * @author phj03
 */

@Service
@RequiredArgsConstructor
@Transactional
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final TeacherRepository teacherRepository;
    private final LectureIndexRepository lectureIndexRepository;
    private final LectureVideoRepository lectureVideoRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final FileRepository fileRepository;

    private final FileManager fileManager;
    private final CacheManager cacheManager;


    @Override
    public List<LectureDTO.Read> readPagedList(LectureDTO.Search rq, Page.Request prq) {

        // [1] 최근 검색어 기록
        if (Objects.nonNull(rq.getMemberId()) && !rq.getKeyword().isBlank())
            cacheManager.recordRecentKeyword(rq.getMemberId(), rq.getKeyword());

        // [2] 검색 수행



        return List.of();
    }


    @Override
    public List<String> readRecentKeywords(Long memberId) {
        return cacheManager.getRecentKeywords(memberId);
    }


    @Override
    public List<LectureDTO.Read> readRecentLectures(Subject subject, int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 과목 기반으로 최근 강의 정렬
        return lectureRepository.findRecentLecturesBySubject(subject, LectureRegisterStatus.REGISTERED, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }


    @Override
    public List<LectureDTO.Read> readBestLectures(Subject subject, int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 과목 기반으로 BEST 강의 정렬
        return lectureRepository.findBestLecturesBySubject(subject, LectureRegisterStatus.REGISTERED, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<LectureDTO.Read> readAllRecentLectures(int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 모든 강의 중 카운트만큼 최신순 정렬
        return lectureRepository.findAllByOrderByPublishDateDesc(pageable, LectureRegisterStatus.REGISTERED)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<LectureDTO.Read> readAllPopularLectures(int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 모든 강의 중 카운트만큼 인기순 정렬
        return lectureRepository.findAllByOrderByTotalStudentsDesc(pageable, LectureRegisterStatus.REGISTERED)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<LectureDTO.Read> readBestLectures(Long teacherId, int count) {
        // [1] 정렬을 위해 필요한 변수 불러오기
        Pageable pageable = PageRequest.of(0, count);
        // [2] 해당하는 선생님 ID를 통해 Best 강의 조회 후 리스팅
        return lectureRepository.findBestLecturesByTeacherId(teacherId, LectureRegisterStatus.REGISTERED, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }


    @Override
    public List<LectureDTO.Read> readRecentLectures(Long teacherId, int count) {
        // [1] 정렬을 위해 필요한 변수 불러오기
        Pageable pageable = PageRequest.of(0, count);
        // [2] 해당하는 선생님 ID를 통해 최근 강의 조회 후 리스팅
        return lectureRepository.findRecentLecturesByTeacherId(teacherId, LectureRegisterStatus.REGISTERED, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }


    @Override
    public LectureDTO.Register registerLecture(LectureDTO.Register dto, MemberProfile profile, LectureRegisterStatus status) {
        Teacher teacher = teacherRepository.findById(profile.getTeacherId()).orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));
        Lecture lecture = Lecture.builder()
                .teacher(teacher)
                .title(dto.getTitle())
                .price(dto.getPrice())
                .difficulty(dto.getDifficulty())
                .subject(dto.getSubject())
                .lectureTarget(dto.getTarget() != null ? dto.getTarget() : LectureTarget.HIGH1)
                .description(dto.getDescription())
                .subjectDetail(dto.getSubjectDetail())
                .build();

        lectureRepository.save(lecture);

        if (dto.getCurriculumTitles() != null && !dto.getCurriculumTitles().isEmpty()) {
            List<LectureIndex> indexes = new ArrayList<>();
            Long index = 1L;

            for (String title : dto.getCurriculumTitles()) {
                indexes.add(LectureIndex.builder()
                        .lecture(lecture)
                        .indexNumber(index++)
                        .indexTitle(title)
                        .build());
            }

            lectureIndexRepository.saveAll(indexes);
        }
        return dto;
    }


    /* 리뷰 퍼센트 계산 - 강의페이지 */
    @Override
    public Map<Integer, Double> getRatingPercentage(Long lectureId) {
        // 1. 총 리뷰 수
        long totalReviews = lectureReviewRepository.countByLecture_LectureId(lectureId);
        if(totalReviews == 0) return Map.of(5,0.0,4,0.0,3,0.0,2,0.0,1,0.0);

        // 2. 각 평점별 리뷰 개수
        Map<Integer, Long> countMap = Map.of(
                5, lectureReviewRepository.countByLecture_LectureIdAndRating(lectureId, 5),
                4, lectureReviewRepository.countByLecture_LectureIdAndRating(lectureId, 4),
                3, lectureReviewRepository.countByLecture_LectureIdAndRating(lectureId, 3),
                2, lectureReviewRepository.countByLecture_LectureIdAndRating(lectureId, 2),
                1, lectureReviewRepository.countByLecture_LectureIdAndRating(lectureId, 1)
        );

        // 3. 퍼센트 계산
        Map<Integer, Double> percentMap = new HashMap<>();
        countMap.forEach((star, count) -> percentMap.put(star, (count * 100.0) / totalReviews));
        return percentMap;
    }



    @Override
    public LectureDTO.ReadLectureInfo readLectureInfo(Long lectureId, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        return LectureDTO.ReadLectureInfo.builder()
                .teacherName(teacher.getMember().getNickname())
                .teacherId(teacherId)
                .title(lecture.getTitle())
                .description(lecture.getDescription())
                .target(lecture.getLectureTarget())
                .difficulty(lecture.getDifficulty())
                .subject(lecture.getSubject())
                .price(lecture.getPrice())
                .build();
    }

    @Override
    public void updateThumbnail(Long lectureId, Long teacherId, MultipartFile thumbnailFile) {
        // 1️⃣ 강의 조회
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        // 2️⃣ 로그인한 강사의 강의인지 확인
        if (!lecture.getTeacher().getTeacherId().equals(teacherId)) {
            throw new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND);
        }

        // 3️⃣ 기존 썸네일 확인
        File thumbnail = lecture.getThumbnailFile();

        if (Objects.isNull(thumbnail)) {
            // 4️⃣ 없으면 새로 등록
            FileDTO.Upload uploadDTO =
                    DTOMapper.toUploadDTO(thumbnailFile, lectureId, Entity.LECTURE, FileType.THUMBNAIL);

            File savedFile = fileRepository.save(DTOMapper.toEntity(uploadDTO));

            // 5️⃣ 강의에 연결
            lecture.updateThumbnail(savedFile);

            // 6️⃣ 실제 파일 업로드
            fileManager.upload(thumbnailFile, uploadDTO.getStoreName(), uploadDTO.getEntity().getName());

        } else {
            // 7️⃣ 기존 파일 덮어쓰기
            thumbnail.update(
                    thumbnailFile.getOriginalFilename(),
                    StrUtils.extractFileExt(thumbnailFile.getOriginalFilename()),
                    thumbnailFile.getSize()
            );

            fileManager.upload(thumbnailFile, thumbnail.getStoreName(), thumbnail.getEntity().getName());
        }
    }

    public String getLectureThumbnailPath(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의 ID: " + lectureId));

        if (lecture.getThumbnailFile() == null) {
            return null;
        }

        return lecture.getThumbnailFile().getFilePath();
    }

}
