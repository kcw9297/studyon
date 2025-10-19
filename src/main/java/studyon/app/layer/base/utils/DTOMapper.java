package studyon.app.layer.base.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Entity;
import studyon.app.layer.domain.category.Category;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_category.LectureCategory;
import studyon.app.layer.domain.lecture_category.LectureCategoryDTO;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
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
import studyon.app.layer.domain.payment.Payment;
import studyon.app.layer.domain.payment.PaymentDTO;
import studyon.app.layer.domain.payment_details.PaymentDetails;
import studyon.app.layer.domain.payment_details.PaymentDetailsDTO;
import studyon.app.layer.domain.payment_refund.PaymentRefund;
import studyon.app.layer.domain.payment_refund.PaymentRefundDTO;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.Objects;
import java.util.UUID;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-17) : khj00 추가 작성(LogDTO toReadDTO() 이후)
 */

/**
 * 특정 객체를 DTO 혹은 Entity 으로의 매핑 로직 처리
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DTOMapper {

    /*
        !! toEntity() 메소드
     */

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

    public static LectureCategory toEntity(Lecture lecture, Category category) {
        return LectureCategory.builder()
                .lecture(lecture)
                .category(category)
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

    public static Payment toEntity(PaymentDTO.Write dto, Member member, Lecture lecture) {
        return Payment.builder()
                .originalPrice(dto.getOriginalPrice())
                .discountPrice(dto.getDiscountPrice())
                .paidPrice(dto.getPaidPrice())
                .member(member)
                .lecture(lecture)
                .build();
    }

    public static PaymentDetails toEntity(PaymentDetailsDTO.Write dto, Payment payment) {
        return PaymentDetails.builder()
                .paymentApiResult(dto.getPaymentApiResult())
                .payment(payment)
                .build();
    }

    public static PaymentRefund toEntity(PaymentRefundDTO.Write dto, Payment payment, PaymentDetails paymentDetails) {
        return PaymentRefund.builder()
                .refundReason(dto.getRefundReason())
                .refundPrice(dto.getRefundPrice())
                .payment(payment)
                .paymentDetails(paymentDetails)
                .build();
    }

    /*
        !! toReadDTO() 메소드
     */
    
    public static MemberDTO.Read toReadDTO(Member entity) {
        return MemberDTO.Read.builder()
                .targetId(entity.getMemberId())
                .targetEntity(Entity.MEMBER)
                .memberId(entity.getMemberId())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .lastLoginAt(entity.getLastLoginAt())
                .cdate(entity.getCdate())
                .provider(entity.getProvider())
                .build();
    }

    public static FileDTO.Upload toUploadDTO(MultipartFile file, Long entityId, Entity entity) {

        // [1] 파일 정보 추출
        String originalName = file.getOriginalFilename();
        String ext = Objects.isNull(originalName) || originalName.isBlank() ?
                "" : originalName.substring(originalName.lastIndexOf(".") + 1);
        String storeName = "%s.%s".formatted(UUID.randomUUID().toString(), ext);

        // [2] 추출 정보 기반 DTO 생성 및 반환
        return FileDTO.Upload.builder()
                .originalName(originalName)
                .storeName(storeName)
                .ext(ext)
                .size(file.getSize())
                .entityId(entityId)
                .entity(entity)
                .build();
    }

    public static FileDTO.Read toReadDTO(File entity) {

        return FileDTO.Read.builder()
                .targetId(entity.getFileId())
                .targetEntity(Entity.MEMBER)
                .fileId(entity.getFileId())
                .originalName(entity.getOriginalName())
                .storeName(entity.getStoreName())
                .ext(entity.getExt())
                .size(entity.getSize())
                .entity(entity.getEntity())
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
                .build();
    }

    public static LectureCategoryDTO.Read toReadDTO(LectureCategory entity) {
        return LectureCategoryDTO.Read.builder()
                .lectureCategoryId(entity.getLectureCategoryId())
                .lectureId(entity.getLecture().getLectureId())
                .categoryId(entity.getCategory().getCategoryId())
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
                .lectureQnaId(entity.getLectureQnaId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .answerCount(entity.getAnswerCount())
                .viewCount(entity.getViewCount())
                .viewCount(entity.getViewCount())
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

    public static TeacherDTO.Read toReadDTO(Teacher entity) {
        return TeacherDTO.Read.builder()
                .teacherId(entity.getTeacherId())
                .memberId(entity.getMember().getMemberId())
                .description(entity.getDescription())
                .subject(entity.getSubject())
                .totalStudents(entity.getTotalStudents())
                .totalReview(entity.getTotalReview())
                .averageRating(entity.getAverageRating())
                .build();
    }

    public static PaymentDTO.Read toReadDTO(Payment entity) {

        return PaymentDTO.Read.builder()
                .paymentId(entity.getPaymentId())
                .paidAt(entity.getPaidAt())
                .originalPrice(entity.getOriginalPrice())
                .paidPrice(entity.getPaidPrice())
                .discountPrice(entity.getDiscountPrice())
                .memberId(entity.getMember().getMemberId())
                .lectureId(entity.getLecture().getLectureId())
                .build();
    }


    public static PaymentDetailsDTO.Read toReadDTO(PaymentDetails entity) {

        return PaymentDetailsDTO.Read.builder()
                .paymentDetailId(entity.getPaymentDetailId())
                .paymentId(entity.getPayment().getPaymentId())
                .paymentApiResult(entity.getPaymentApiResult())
                .build();
    }


    public static PaymentRefundDTO.Read toReadDTO(PaymentRefund entity) {
        return PaymentRefundDTO.Read.builder()
                .orderRefundId(entity.getOrderRefundId())
                .refundReason(entity.getRefundReason())
                .refundPrice(entity.getRefundPrice())
                .isRefunded(entity.isRefunded())
                .createdAt(entity.getCreatedAt())
                .paymentId(entity.getPayment().getPaymentId())
                .paymentDetailId(entity.getPaymentDetails().getPaymentDetailId())
                .build();
    }

    public static MemberProfile toMemberProfileDTO(Member member) {
        return MemberProfile.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .provider(member.getProvider())
                .role(member.getRole())
                .build();
    }
}
