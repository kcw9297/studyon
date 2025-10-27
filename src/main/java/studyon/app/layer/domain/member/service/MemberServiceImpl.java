package studyon.app.layer.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.*;
import studyon.app.common.utils.StrUtils;
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
    @Cacheable(value = "member:profile", key = "#memberId") // 캐시 등록
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
                    .findByMemberIdWithMember(memberId)
                    .orElseThrow(() -> new BusinessLogicException(AppStatus.TEACHER_NOT_FOUND));

            // 선생님 프로필 정보 반환
            return DTOMapper.toMemberProfile(member, teacher);

            // 선생님 회원 외에는 일반적인 프로필 정보 반환
        } else {
            return DTOMapper.toMemberProfile(member);
        }
    }

    @Override
    public void editPassword(String email, String newPassword) {

        memberRepository
                .findByEmailAndProvider(email, Provider.NORMAL)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .updatePassword(passwordEncoder.encode(newPassword)); // 정상 조회 시 비밀번호 갱신
    }


    @Override
    @CacheEvict(value = "member:profile" , key = "#memberId") // 캐시 삭제
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
            member.updateProfileImage(fileRepository.save(DTOMapper.toEntity(uploadDTO)));

            // 물리적 저장 수행
            fileManager.upload(profileImageFile, uploadDTO.getStoreName(), uploadDTO.getEntity().getName());


            // [2-2] 새롭게 존재하면 파일 정보만 갱신
        } else {

            // 바뀌는 파일 원래 정보만 변경 (저장 파일명은 바꾸지 않음)
            profileImage.update(
                    profileImageFile.getOriginalFilename(),
                    StrUtils.extractFileExt(profileImageFile.getOriginalFilename()),
                    profileImageFile.getSize()
            );

            // 물리적 파일 덮어쓰기 수행 (같은 파일명으로 재업로드)
            fileManager.upload(profileImageFile, profileImage.getStoreName(), profileImage.getEntity().getName());
        }
    }



    @Override
    @CacheEvict(value = "member:profile" , key = "#memberId") // 캐시 삭제
    public void editNickname(Long memberId, String nickname) {

        // [1] 닉네임 변경 대상 회원조회
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));

        // [2] 닉네임 중복 검증
        if (Objects.equals(member.getNickname(), nickname))
            throw new BusinessLogicException(AppStatus.MEMBER_DUPLICATE_NICKNAME);

        // [3] 닉네임 갱신
        member.updateNickname(nickname);
    }


    @Override
    @CacheEvict(value = "member:profile" , key = "#memberId") // 캐시 삭제
    public void withdraw(Long memberId) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .withdraw();
    }


    @Override
    @CacheEvict(value = "member:profile" , key = "#memberId") // 캐시 삭제
    public void recover(Long memberId) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .recover();
    }

    @Override
    @Transactional
    public Page.Response<MemberDTO.Read> search(Page.Request prq, MemberDTO.Search rq) {
        log.info("🔍 [SERVICE] 회원 검색 실행: filter={}, keyword={}, role={}, isActive={}",
                rq.getFilter(), rq.getKeyword(), rq.getRole(), rq.getIsActive());


        // [1] MyBatis 매퍼 호출
        List<MemberDTO.Read> members = memberMapper.selectBySearch(rq, prq);

        // [2] 총 카운트 조회
        int count = memberMapper.countBySearch(rq);
        log.info("📘 [DEBUG] page={}, size={}, startPage={}", prq.getPage(), prq.getSize(), prq.getStartPage());


        log.info("📗 [DEBUG] 검색 결과 count: {}", members.size());

        log.info("📘 [DEBUG] 총 데이터 수: {}", count);
        // [3] 페이징 응답 생성
        return Page.Response.create(members, prq.getPage(), prq.getSize(), count);
    }
}
