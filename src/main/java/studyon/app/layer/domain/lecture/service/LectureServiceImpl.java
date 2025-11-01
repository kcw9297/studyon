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
import studyon.app.layer.domain.lecture.mapper.LectureMapper;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.lecture_index.repository.LectureIndexRepository;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;
import studyon.app.layer.domain.lecture_video.repository.LectureVideoRepository;
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
 *
 * @author phj03
 * @version 1.2
 */

@Service
@RequiredArgsConstructor
@Transactional
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper;
    private final TeacherRepository teacherRepository;
    private final LectureIndexRepository lectureIndexRepository;
    private final LectureVideoRepository lectureVideoRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final FileRepository fileRepository;

    private final FileManager fileManager;
    private final CacheManager cacheManager;


    @Override
    public Page.Response<LectureDTO.Read> readPagedList(LectureDTO.Search rq, Page.Request prq) {

        // [1] 최근 검색어 기록
        if (Objects.nonNull(rq.getMemberId()) && !rq.getKeyword().isBlank())
            cacheManager.recordRecentKeyword(rq.getMemberId(), rq.getKeyword());

        // [2] 검색 수행
        List<LectureDTO.Read> page = lectureMapper.selectAll(rq, prq);
        Integer count = lectureMapper.countAll(rq);

        // [3] 검색 결과 반환
        return Page.Response.create(page, prq.getPage(), prq.getSize(), count);
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
    public Long create(LectureDTO.Create dto) {

        // [1] 선생님 조회
        Teacher teacher = teacherRepository
                .findById(dto.getTeacherId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));

        // [2] 강의저장 수행
        Lecture lecture = DTOMapper.toEntity(dto, teacher);
        Lecture savedLecture = lectureRepository.save(lecture);

        // [3] 강의 인덱스 저장
        if (dto.getCurriculumTitles() != null && !dto.getCurriculumTitles().isEmpty()) {
            List<LectureIndex> indexes = new ArrayList<>();
            long index = 1L;

            for (String title : dto.getCurriculumTitles()) {
                indexes.add(LectureIndex.builder()
                        .lecture(lecture)
                        .indexNumber(index++)
                        .indexTitle(title)
                        .build());
            }
            lectureIndexRepository.saveAll(indexes);
        }

        // [4] 생성된 강의번호 반환
        return savedLecture.getLectureId();
    }


    /* 리뷰 퍼센트 계산 - 강의페이지 */
    @Override
    public Map<Integer, Double> getRatingPercentage(Long lectureId) {
        // 1. 총 리뷰 수
        long totalReviews = lectureReviewRepository.countByLecture_LectureId(lectureId);
        if (totalReviews == 0) return Map.of(5, 0.0, 4, 0.0, 3, 0.0, 2, 0.0, 1, 0.0);

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
                .lectureRegisterStatus(lecture.getLectureRegisterStatus())
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

    @Override
    public List<LectureDTO.Read> readBestLecturesBySubject(String subject, int count) {
        Pageable pageable = PageRequest.of(0, count);

        return lectureRepository
                .findBestLecturesBySubjectAlgorithm(Subject.valueOf(subject), LectureRegisterStatus.REGISTERED, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // DTO 내부에서 teacher.member.profileImage 유지
                .collect(Collectors.toList());
    }

    @Override
    public List<LectureDTO.Read> readBestLecturesByTeacher(Long teacherId, int count) {
        Pageable pageable = PageRequest.of(0, count);

        return lectureRepository
                .findBestLecturesByTeacherAlgorithm(teacherId, LectureRegisterStatus.REGISTERED, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // DTO 내부에서 teacher.member.profileImage 유지
                .collect(Collectors.toList());
    }


    @Override
    public void startSale(Long lectureId) {

        // [1] 강의 정보 조회
        Lecture lecture = lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        // [2] 현재 강의상태 검증
        if (Objects.equals(lecture.getLectureRegisterStatus(), LectureRegisterStatus.REJECTED))
            throw new BusinessLogicException(AppStatus.LECTURE_REJECT_NOW);

        if (!Objects.equals(lecture.getLectureRegisterStatus(), LectureRegisterStatus.REGISTERED))
            throw new BusinessLogicException(AppStatus.LECTURE_SALE_START_NOT_AVAILABLE);

        // [3] 판매 상태로 변경
        lecture.startSale();
    }


    @Override
    public void stopSale(Long lectureId) {

        lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND))
                .stopSale();
    }


    @Override
    public void pending(Long lectureId) {

        // [1] 강의 정보 조회
        Lecture lecture = lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        // [2] 상태 검증 (의도하지 않은 방향으로 상태 갱신이 되는 경우)
        if (!lecture.getLectureRegisterStatus().equals(LectureRegisterStatus.UNREGISTERED))
            throw new BusinessLogicException(AppStatus.LECTURE_STATE_NOT_EDITABLE);

        // [3] 상태갱신 수행
        lecture.pending();
    }

    @Override
    public void register(Long lectureId) {

        // [1] 강의 정보 조회
        Lecture lecture = lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        // [2] 상태 검증 (의도하지 않은 방향으로 상태 갱신이 되는 경우)
        // 변경이 가능한 현재 상태
        List<LectureRegisterStatus> available = List.of(LectureRegisterStatus.PENDING, LectureRegisterStatus.REJECTED);

        // 리스트 외의 상태에서 등록 요청을 하는 경우 검증
        if (!available.contains(lecture.getLectureRegisterStatus()))
            throw new BusinessLogicException(AppStatus.LECTURE_STATE_NOT_EDITABLE);

        // [3] 상태갱신 수행
        lecture.register();
    }

    @Override
    public void reject(Long lectureId, String rejectReason) {

        lectureRepository
                .findById(lectureId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND))
                .reject(rejectReason);
    }


    @Override
    public Map<String, Long> readLectureCountBySubject() {
        return lectureRepository.findLectureCountBySubject().stream()
                .collect(Collectors.toMap(
                        row -> row.get("subject").toString(),
                        row -> (Long) row.get("cnt")));
    }

    /**
     * 난이도별 강의 수 조회
     * 관리자 통계용 (doughnut chart)
     */
    @Override
    public Map<String, Long> readLectureCountByDifficulty() {
        return lectureRepository.findLectureCountByDifficulty().stream()
                .collect(Collectors.toMap(
                        row -> Difficulty.valueOf(row.get("difficulty").toString()).getValue(),
                        row -> (Long) row.get("cnt")
                ));
    }

    /**
     * 등록 상태별 강의 수 조회
     * 관리자 통계용 (pie chart)
     */
    @Override
    public Map<String, Long> readLectureCountByStatus() {
        return lectureRepository.findLectureCountByStatus().stream()
                .collect(Collectors.toMap(
                        row -> LectureRegisterStatus.valueOf(row.get("status").toString()).getValue(),
                        row -> (Long) row.get("cnt")
                ));
    }

    /**
     * 강의 평점 상위 TOP5 조회
     * 관리자 통계용
     */
    @Override
    public List<LectureDTO.Read> readTopRatedLectures(int count) {
        // [1] DB 조회 (상위 n개)
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "averageRate"));

        return lectureRepository.findAll(pageable)
                .getContent()
                .stream()
                .sorted(Comparator.comparing(Lecture::getAverageRate).reversed()) // 혹시 null-safe 정렬
                .limit(count)
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> readLectureCountByTarget() {
        // 학년 한글 반환
        return lectureRepository.findLectureCountByTarget().stream()
                .collect(Collectors.toMap(
                        row -> LectureTarget.valueOf(row.get("target").toString()).getValue(),
                        row -> (Long) row.get("cnt")
                ));
    }
}
