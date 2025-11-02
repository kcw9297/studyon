package studyon.app.layer.domain.teacher.service;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.LectureRegisterStatus;
import studyon.app.common.enums.Subject;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.mapper.TeacherMapper;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.List;
import java.util.stream.Collectors;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 서비스 구현체
 * @version 1.0
 * @author khj00
 */
@Slf4j
@lombok.extern.slf4j.Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final LectureRepository lectureRepository;
    private final TeacherMapper teacherMapper;

    /**
     * 모든 선생님 조회
     * @return 선생님 리스트
     */

    @Override
    public Page.Response<TeacherDTO.Read> readPagedList(TeacherDTO.Search rq, Page.Request prq) {
        // [1] 리스팅 카운트용 변수
        log.info("[SERVICE] 강사 목록 조회 요청 - keyword={}, subject={}",
                rq.getKeyword(),
                rq.getSubject() != null ? rq.getSubject().getValue() : "전체");
        List<TeacherDTO.Read> list = teacherMapper.findBySearch(rq, prq);
        Integer totalCount = teacherMapper.countBySearch(rq);
        // [2] 레포지토리에서 모든 선생님 정보 가져오기
        return Page.Response.create(list, prq.getPage(), prq.getSize(), totalCount);
    }

    /**
     * 과목별 선생님 조회
     * @return 해당 선생님 리스트
     */
    @Override
    public List<TeacherDTO.Read> readTeachersBySubject(Subject subject) {
//        // [1] 리스팅 카운트용 변수
//        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.ASC, "teacherId"));
        // [2] 레포지토리에서 과목별로 선생님 정보 가져와서 DTO 변환 후 리스팅, 'teacherId' 필드를 기준으로 오름차순 정렬
        return teacherRepository.findBySubjectWithMember(subject).stream()
                .map(DTOMapper::toReadDTO)
                .peek(dto -> {
                    if (dto.getMemberId() != null) {
                        // memberId 기반으로 파일 경로 조회
                        String filePath = teacherRepository.findProfileImagePathByMemberId(dto.getMemberId());
                        dto.setThumbnailPath(filePath != null
                                ? filePath
                                : "/img/png/default_member_profile_image.png"); // 기본 이미지
                    }
                })
                .collect(Collectors.toList());
    }
    /**
     * 선생님 프로필 가져오기
     * @param teacherId 선생님 아이디
     * @return 해당 선생님 리스트
     */
    @Override
    public TeacherDTO.Read read(Long teacherId) {
        // [1] 해당되는 ID에 따른 선생님 프로필 불러오기
        return teacherRepository.findById(teacherId)
                .map(DTOMapper::toReadDTO)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));
    }

    /**
     * 선생님 프로필 업데이트(필요시)
     * @param teacherId 선생님 아이디
     * @param dto 선생님 정보를 담은 DTO - 업데이트용
     */
    @Override
    public void update(Long teacherId, TeacherDTO.Edit dto) {
        // [1] 선생님 아이디로 조회 후 정보 수정 및 저장
        teacherRepository.findById(teacherId)
                .ifPresentOrElse(
                        teacher -> teacher.updateInfo(dto.getSubject(), dto.getDescription()),
                        () -> { throw new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND); }
                );
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDTO.LectureListResponse getLectureListByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));

        List<Lecture> pending = lectureRepository.findByTeacherAndLectureRegisterStatus(teacher, LectureRegisterStatus.PENDING);
        List<Lecture> registered = lectureRepository.findByTeacherAndLectureRegisterStatus(teacher, LectureRegisterStatus.REGISTERED);
        List<Lecture> unregistered = lectureRepository.findByTeacherAndLectureRegisterStatus(teacher, LectureRegisterStatus.UNREGISTERED);


        return TeacherDTO.LectureListResponse.builder()
                .teacherId(teacherId)
                .nickname(teacher.getMember().getNickname())
                .pending(mapToLectureSimpleList(pending))
                .registered(mapToLectureSimpleList(registered))
                .unregistered(mapToLectureSimpleList(unregistered))
                .build();
    }


    private List<TeacherDTO.LectureListResponse.LectureSimple> mapToLectureSimpleList(List<Lecture> lectures) {
//        return lectures.stream()
//                .map(l -> TeacherDTO.LectureListResponse.LectureSimple.builder()
//                        .lectureId(l.getLectureId())
//                        .title(l.getTitle())
//                        .status(l.getLectureRegisterStatus().name())
//                        .build())
//                .toList();
        return lectures.stream()
                .map(l -> {
                    String thumbnailPath = lectureRepository.findThumbnailPathByLectureId(l.getLectureId())
                            .orElse("img/png/default_lecture_thumbnail.png");

                    return TeacherDTO.LectureListResponse.LectureSimple.builder()
                            .lectureId(l.getLectureId())
                            .title(l.getTitle())
                            .status(l.getLectureRegisterStatus().name())
                            .thumbnailImagePath(thumbnailPath)
                            .build();
                })
                .toList();
    }


    @Override
    public TeacherDTO.TeacherManagementProfile readProfile(Long memberId) {
        Teacher teacher = teacherRepository.findByMemberIdWithMember(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));
        Member member = teacher.getMember();
        Long lectureCount = lectureRepository.count(teacher.getTeacherId(), LectureRegisterStatus.REGISTERED);

        return TeacherDTO.TeacherManagementProfile.builder()
                .teacherId(teacher.getTeacherId())
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .description(teacher.getDescription())
                .subject(teacher.getSubject())
                .lectureCount(lectureCount)
                .totalStudent(teacher.getTotalStudents())
                .averageRating(teacher.getAverageRating())
                .build();
    }


    public TeacherDTO.ReadDetail readTeacherDetail(Long teacherId) {
        // 🔹 Teacher + Member + ProfileImage 조인해서 한 번에 가져오는 쿼리 (fetch join)
        Teacher teacher = teacherRepository.findByIdWithMemberAndProfileImage(teacherId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));

        Member member = teacher.getMember();
        Long lectureCount = lectureRepository.count(teacher.getTeacherId(), LectureRegisterStatus.REGISTERED);

        // ✅ 프로필 이미지 경로 확인
        String profilePath = null;
        if (member.getProfileImage() != null) {
            profilePath = member.getProfileImage().getFilePath(); // File 엔티티 필드 기준
        }

        return TeacherDTO.ReadDetail.builder()
                .teacherId(teacher.getTeacherId())
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .description(teacher.getDescription())
                .subject(teacher.getSubject())
                .lectureCount(lectureCount)
                .totalStudents(teacher.getTotalStudents())
                .averageRating(teacher.getAverageRating())
                .profileImagePath(profilePath != null ? profilePath : "/img/png/default_member_profile_image.png")
                .build();
    }


    @Transactional(readOnly = true)
    public TeacherDTO.ReadDetail readDetail(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));

        Member member = teacher.getMember();

        // ✅ 프로필 이미지 경로 처리
        String profilePath = null;
        if (member.getProfileImage() != null) {
            profilePath = member.getProfileImage().getFilePath(); // File 엔티티 기준
        }

        // ✅ 강의 수 계산
        Long lectureCount = lectureRepository.count(teacherId, LectureRegisterStatus.REGISTERED);

        // ✅ DTO 빌드
        return TeacherDTO.ReadDetail.builder()
                .teacherId(teacher.getTeacherId())
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .description(teacher.getDescription())
                .subject(teacher.getSubject())
                .lectureCount(lectureCount)
                .totalStudents(teacher.getTotalStudents())
                .averageRating(teacher.getAverageRating())
                .profileImagePath(profilePath != null ? profilePath : "/img/png/default_member_profile_image.png")
                .build();
    }





}
