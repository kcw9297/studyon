package studyon.app.layer.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.enums.Role;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.dto.Page;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.member.mapper.MemberMapper;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.List;
import java.util.Objects;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 회원 프로필 조회 로직 변경
 */

/**
 * 회원 서비스 구현체
 * @version 1.1
 * @author kcw97
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    // repository
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;
    private final TeacherRepository teacherRepository;
    private final MemberMapper memberMapper;

    // 기타 필요 의존성
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;
    private final FileManager fileManager;

    @Override
    @Transactional(readOnly = true)
    public MemberDTO.Read read(Long memberId) {
        return memberRepository
                .findById(memberId)
                .map(DTOMapper::toReadDto)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));
    }


    @Override
    @Transactional(readOnly = true)
    public Page.Response<MemberDTO.Read> readPagedList(MemberDTO.Search rq, Page.Request prq) {

        // [1] 회원 페이징
        List<MemberDTO.Read> memberReads = memberMapper.selectAll(rq, prq);

        // [2] 페이징 결과 기반 카운트
        Integer count = memberMapper.countAll(rq);

        // [3] 조회 결과 반환
        return Page.Response.create(memberReads, prq.getPage(), prq.getSize(), count);
    }


    @Override
    @Transactional(readOnly = true)
    public MemberProfile readProfile(Long memberId) {

        // [1] 회원 정보 조회
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));

        // [2] 만약 선생님 회원이면, 선생님 회원정보 조회
        if (Objects.equals(member.getRole(), Role.ROLE_TEACHER)) {

            // 선생님 정보 조회
            Teacher teacher = teacherRepository
                    .findByMemberIdWithMemberAndProfileImage(memberId)
                    .orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));

            // 선생님 프로필 정보 반환
            return DTOMapper.toMemberProfile(member, teacher);

            // 선생님 회원 외에는 일반적인 프로필 정보 반환
        } else {
            return DTOMapper.toMemberProfile(member);
        }
    }


    @Override
    public String initPassword(Long memberId) {

        // [1] 초기화할 패스워드 문자열 생성
        String newPassword = StrUtils.createShortUUID();

        // [2] 회원 조회 후 초기화 수행
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .updatePassword(passwordEncoder.encode(newPassword)); // 암호화 후 초기화 수행

        // [3] 초기화에 성공한 비밀번호 반환
        return newPassword;
    }


    @Override
    public void editProfileImage(Long memberId, MultipartFile profileImageFile) {

        // [1] 회원 데이터 조회
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));

        // [2] 프로필 이미지 존재여부 비교
        File profileImage = member.getProfileImage();

        // [2-1] 만약 존재하지 않으면 새롭게 생성
        if (Objects.isNull(profileImage)) {

            // 업로드 요청 DTO 생성
            FileDTO.Upload uploadDTO =
                    DTOMapper.toUploadDTO(profileImageFile, memberId, Entity.MEMBER, FileType.PROFILE);

            // DB 내 파일정보 생성
            fileRepository.save(DTOMapper.toEntity(uploadDTO));

            // 물리적 저장 수행
            fileManager.upload(uploadDTO);


            // [2-2] 새롭게 존재하면 파일 정보만 갱신
        } else {

            // 바뀌는 파일 원래 정보만 변경 (저장 파일명은 바꾸지 않음)
            profileImage.update(
                    profileImageFile.getOriginalFilename(),
                    StrUtils.extractFileExt(profileImageFile.getOriginalFilename()),
                    profileImageFile.getSize()
            );

            // 물리적 파일 덮어쓰기 수행 (같은 파일명으로 재업로드)
            fileManager.upload(DTOMapper.toUploadDTO(profileImage, profileImageFile));
        }
    }



    @Override
    public void editNickname(Long memberId, String nickname) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .updateNickname(nickname);
    }


    @Override
    public void withdraw(Long memberId) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .withdraw();
    }


    @Override
    public void recover(Long memberId) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .recover();
    }



}
