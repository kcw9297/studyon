package studyon.app.layer.domain.member;

import lombok.*;
import studyon.app.common.enums.Provider;
import studyon.app.common.enums.Role;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.file.FileDTO;

import java.io.Serial;
import java.io.Serializable;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 프로필 이미지 정보, 선생님 번호 정보 추가
 */

/**
 * 로그인 회원의 프로필 정보를 담은 DTO
 * @version 1.1
 * @author kcw97
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class MemberProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 기본 회원 정보
    private Long memberId;
    private String nickname;
    private String email;
    private Provider provider;
    private Role role;
    private Subject teacherSubject;
    private FileDTO.Read profileImage;

    // 선생님 회원인 경우 추가 정보
    private Long teacherId;
}
