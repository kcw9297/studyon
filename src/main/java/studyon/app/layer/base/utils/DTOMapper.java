package studyon.app.layer.base.utils;

import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.domain.auth.AuthDTO;
import studyon.app.layer.domain.banner.Banner;
import studyon.app.layer.domain.banner.BannerDTO;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
import studyon.app.layer.domain.lecture_like.LectureLike;
import studyon.app.layer.domain.lecture_like.LectureLikeDTO;
import studyon.app.layer.domain.lecture_question.LectureQuestion;
import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_video.LectureVideo;
import studyon.app.layer.domain.lecture_video.LectureVideoDTO;
import studyon.app.layer.domain.log.Log;
import studyon.app.layer.domain.log.LogDTO;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.notice.Notice;
import studyon.app.layer.domain.notice.NoticeDTO;
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.Objects;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-17) : khj00 추가 작성(LogDTO toReadDTO() 이후)
 *  ▶ ver 1.2 (2025-10-22) : kcw97 fileDTO 매핑 방식 수정 및 PaymentDetails 삭제
 *  ▶ ver 1.3 (2025-10-23) : kcw97 PaymentRefund 삭제
 *  ▶ ver 1.4 (2025-10-24) : kcw97 File 엔티티 UploadDTO 매핑로직 추가
 */

/**
 * 특정 객체를 DTO 혹은 Entity 으로의 매핑 로직 처리
 * @version 1.4
 * @author kcw97
 */

@NoArgsConstructor
public class DTOMapper {

    /**
     *  !! toEntity() 메소드
     */

    public static Lecture toEntity(LectureDTO.Create dto, Teacher teacher) {

        return Lecture.builder()
                .teacher(teacher)
                .title(dto.getTitle())
                .summary(dto.getSummary())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .difficulty(dto.getDifficulty())
                .lectureTarget(dto.getTarget())
                .subject(teacher.getSubject())
                .subjectDetail(dto.getSubjectDetail())
                .build();
    }



    public static Member toEntity(MemberDTO.Join dto) {
        return Member.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .providerId(dto.getProviderId())
                .provider(dto.getProvider())
                .build();
    }

    public static File toEntity(FileDTO.Upload dto) {
        return File.builder()
                .originalName(dto.getOriginalName())
                .storeName(dto.getStoreName())
                .ext(dto.getExt())
                .size(dto.getSize())
                .entityId(dto.getEntityId())
                .entity(dto.getEntity())
                .fileType(dto.getFileType())
                .filePath(dto.getFilePath())
                .build();
    }

    public static Log toEntity(LogDTO.Generate dto) {
        return Log.builder()
                .email(dto.getEmail())
                .ipAddress(dto.getIpAddress())
                .entityId(dto.getEntityId())
                .entity(dto.getEntity())
                .action(dto.getAction())
                .isSuccess(dto.getIsSuccess())
                .build();
    }


    public static Lecture toEntity(LectureDTO.Write dto, Teacher teacher) {
        return Lecture.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .difficulty(dto.getDifficulty())
                .teacher(teacher)
                .build();
    }

    public static LectureReview toEntity(LectureReviewDTO.Write dto, Lecture lecture, Member member) {
        return LectureReview.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .lecture(lecture)
                .member(member)
                .build();
    }

    public static LectureIndex toEntity(LectureIndexDTO.Write dto, Lecture lecture) {
        return LectureIndex.builder()
                .indexNumber(dto.getIndexNumber())
                .indexTitle(dto.getIndexTitle())
                .lecture(lecture)
                .build();
    }

    public static LectureQuestion toEntity(LectureQuestionDTO.Write dto, Lecture lecture) {
        return LectureQuestion.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .lecture(lecture)
                .build();
    }

    public static LectureVideo toEntity(LectureVideoDTO.Write dto, LectureIndex lectureIndex) {
        return LectureVideo.builder()
                .title(dto.getTitle())
                .seq(dto.getSeq())
                .duration(dto.getDuration())
                .videoUrl(dto.getVideoUrl())
                .lectureIndex(lectureIndex)
                .build();
    }

    public static Teacher toEntity(TeacherDTO.Write dto, Member member) {
        return Teacher.builder()
                .description(dto.getDescription())
                .subject(dto.getSubject())
                .member(member)
                .build();
    }

    public static Payment toEntity(PaymentDTO.Pay dto, Member member, Lecture lecture) {
        return Payment.builder()
                .paymentUid(dto.getPaymentUid())
                .paidAmount(dto.getPaidAmount())
                .paymentApiResult(dto.getPaymentApiResult())
                .member(member)
                .lecture(lecture)
                .build();
    }


    /**
     *  !! toReadDTO() 메소드
     */
    
    public static MemberDTO.Read toReadDTO(Member entity) {
        return MemberDTO.Read.builder()
                .memberId(entity.getMemberId())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .lastLoginAt(entity.getLastLoginAt())
                .cdate(entity.getCdate())
                .provider(entity.getProvider())
                .build();
    }


    public static LogDTO.Read toReadDTO(Log entity) {

        return LogDTO.Read.builder()
                .email(entity.getEmail())
                .ipAddress(entity.getIpAddress())
                .entityId(entity.getEntityId())
                .entity(entity.getEntity())
                .action(entity.getAction())
                .isSuccess(entity.getIsSuccess())
                .actionAt(entity.getActionAt())
                .build();
    }


    public static LectureDTO.Read toReadDTO(Lecture entity) {
        return LectureDTO.Read.builder()
                .lectureId(entity.getLectureId())
                .title(entity.getTitle())
                .summary(entity.getSummary())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .difficulty(entity.getDifficulty())
                .videoCount(entity.getVideoCount())
                .totalStudents(entity.getTotalStudents())
                .totalDuration(entity.getTotalDuration())
                .averageRate(entity.getAverageRate())
                .likeCount(entity.getLikeCount())
                .onSale(entity.getOnSale())
                .publishDate(entity.getPublishDate())
                .teacherId(entity.getTeacher().getTeacherId())
                .teacherNickname(entity.getTeacher().getMember().getNickname())
                .lectureRegisterStatus(entity.getLectureRegisterStatus())
                .thumbnailImagePath(Objects.isNull(entity.getThumbnailFile()) ? null : entity.getThumbnailFile().getFilePath())
                .build();
    }

    public static LectureReviewDTO.Read toReadDTO(LectureReview entity) {
        return LectureReviewDTO.Read.builder()
                .lectureReviewId(entity.getLectureReviewId())
                .content(entity.getContent())
                .rating(entity.getRating())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lectureId(entity.getLecture().getLectureId())
                .memberId(entity.getMember().getMemberId())
                .nickname(entity.getMember().getNickname())
                .build();
    }


    public static LectureIndexDTO.Read toReadDTO(LectureIndex entity) {
        return LectureIndexDTO.Read.builder()
                .lectureIndexId(entity.getLectureIndexId())
                .indexTitle(entity.getIndexTitle())
                .lectureId(entity.getLecture().getLectureId())
                .build();
    }

    public static LectureQuestionDTO.Read toReadDTO(LectureQuestion entity) {
        return LectureQuestionDTO.Read.builder()
                .lectureQuestionId(entity.getLectureQuestionId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .isSolved(entity.getIsSolved())
                .lectureId(entity.getLecture().getLectureId())
                .build();
    }

    public static LectureVideoDTO.Read toReadDTO(LectureVideo entity) {
        return LectureVideoDTO.Read.builder()
                .lectureVideoId(entity.getLectureVideoId())
                .title(entity.getTitle())
                .seq(entity.getSeq())
                .duration(entity.getDuration())
                .videoUrl(entity.getVideoUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lectureIndexId(entity.getLectureIndex().getLectureIndexId())
                .build();
    }

    public static LectureLikeDTO.Read toReadDTO(LectureLike entity) {
        return LectureLikeDTO.Read.builder()
                .lectureLikeId(entity.getLectureLikeId())
                .memberId(entity.getMember().getMemberId())
                .lectureId(entity.getLecture().getLectureId())
                .lecture(toReadDTO(entity.getLecture()))
                .build();
    }

    public static TeacherDTO.Read toReadDTO(Teacher entity) {
        return TeacherDTO.Read.builder()
                .teacherId(entity.getTeacherId())
                .memberId(entity.getMember().getMemberId())
                .description(entity.getDescription())
                .subject(entity.getSubject())
                .totalStudents(entity.getTotalStudents())
                .totalReview(entity.getTotalReview())
                .averageRating(entity.getAverageRating())
                .nickname(entity.getMember().getNickname())
                .build();
    }

    // TODO 패치조인 필요
    public static PaymentDTO.Read toReadDTO(Payment entity) {
        return PaymentDTO.Read.builder()
                .paymentUid(entity.getPaymentUid())
                .lectureId(entity.getLecture().getLectureId())
                .lectureTitle(entity.getLecture().getTitle())
                .nickname(entity.getMember().getNickname())
                .paymentId(entity.getPaymentId())
                .paidAmount(entity.getPaidAmount())
                .cdate(entity.getCdate())
                .isRefunded(entity.getIsRefunded())
                .refundedAt(entity.getRefundedAt())
                .build();
    }


    public static MemberDTO.Read toReadDto(Member member) {
        return MemberDTO.Read.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .lastLoginAt(member.getLastLoginAt())
                .cdate(member.getCdate())
                .withdrawAt(member.getWithdrawAt())
                .isActive(member.getIsActive())
                .provider(member.getProvider())
                .role(member.getRole())
                .build();
    }

    public static MemberProfile toMemberProfile(Member member) {
        MemberProfile.MemberProfileBuilder builder = MemberProfile.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .provider(member.getProvider())
                .role(member.getRole());

        return Objects.isNull(member.getProfileImage()) ?
                builder.build() : builder.profileImage(toReadDTO(member.getProfileImage())).build();

    }

    public static MemberProfile toMemberProfile(Member member, Teacher teacher) {
        MemberProfile.MemberProfileBuilder builder = MemberProfile.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .provider(member.getProvider())
                .role(member.getRole())
                .teacherSubject(teacher.getSubject())
                .teacherId(teacher.getTeacherId());

        return Objects.isNull(member.getProfileImage()) ?
                builder.build() : builder.profileImage(toReadDTO(member.getProfileImage())).build();
    }


    // 파일 업로드 정보 DTO 생성 (파일 저장 전)
    public static FileDTO.Upload toUploadDTO(MultipartFile file, Long entityId, Entity entity, FileType fileType) {

        // 파일 정보 추출
        String originalName = file.getOriginalFilename();
        String ext = StrUtils.extractFileExt(originalName);
        String storeName = "%s.%s".formatted(StrUtils.createUUID(), ext);
        String filePath = "%s/%s".formatted(entity.getName(), storeName);

        // 업로드 정보 DTO 생성 및 반환
        return FileDTO.Upload.builder()
                .file(file)
                .originalName(originalName)
                .storeName(storeName)
                .ext(ext)
                .size(file.getSize())
                .entityId(entityId)
                .entity(entity)
                .fileType(fileType)
                .filePath(filePath)
                .build();
    }


    public static FileDTO.Read toReadDTO(File entity) {

        return FileDTO.Read.builder()
                .fileId(entity.getFileId())
                .originalName(entity.getOriginalName())
                .storeName(entity.getStoreName())
                .ext(entity.getExt())
                .size(entity.getSize())
                .entity(entity.getEntity())
                .fileType(entity.getFileType())
                .filePath(entity.getFilePath())
                .build();
    }

    public static MemberDTO.Join toJoinDTO(AuthDTO.Join rq) {

        return MemberDTO.Join.builder()
                .email(rq.getEmail())
                .password(rq.getPassword())
                .build();
    }

    public static NoticeDTO.Read toReadDTO(Notice entity) {

        File noticeImage = entity.getNoticeImage();

        return NoticeDTO.Read.builder()
                .title(entity.getTitle())
                .idx(entity.getIdx())
                .isActivate(entity.getIsActivate())
                .noticeImage(Objects.isNull(noticeImage) ? null : toReadDTO(noticeImage))
                .build();
    }

    public static BannerDTO.Read toReadDTO(Banner entity) {

        File bannerImage = entity.getBannerImage();

        return BannerDTO.Read.builder()
                .title(entity.getTitle())
                .idx(entity.getIdx())
                .isActivate(entity.getIsActivate())
                .bannerImage(Objects.isNull(bannerImage) ? null : toReadDTO(bannerImage))
                .build();
    }

    public static FileDTO.Remove toRemoveDTO(File entity) {
        return FileDTO.Remove.builder()
                .fileId(entity.getFileId())
                .filePath(entity.getFilePath())
                .build();
    }
}
