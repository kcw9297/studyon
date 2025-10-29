package studyon.app.layer.domain.member_lecture;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberLectureId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(nullable = false)
    private Double progress;  // 진도율 (0~100%)

    @Column(nullable = false)
    private Boolean completed;  // 수강 완료 여부

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime enrolledAt;  // 등록일시

    @Builder
    public MemberLecture(Member member, Lecture lecture) {
        this.member = member;
        this.lecture = lecture;
        this.progress = 0.0;
        this.completed = false;
    }
}
