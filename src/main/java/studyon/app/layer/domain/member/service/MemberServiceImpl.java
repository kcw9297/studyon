package studyon.app.layer.domain.member.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.*;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.mapper.MemberMapper;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 회원 프로필 조회 로직 변경
 *  ▶ ver 1.2 (2025-10-28) : khj00 관리자 회원 목록 조회/검색/PDF 다운로드 로직 추가
 */

/**
 * 회원 서비스 구현체
 * @version 1.2
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
        List<MemberDTO.Read> memberReads;

        // [2] 페이징 결과 기반 카운트
        Integer count;

        // [3] 필터링용 변수
        boolean hasFilter =
                (rq.getKeyword() != null && !rq.getKeyword().isBlank()) ||
                        (rq.getRole() != null && !rq.getRole().isBlank()) ||
                        (rq.getIsActive() != null && !rq.getIsActive().isBlank());

        if (hasFilter) {
            // 검색 조건이 있는 경우 → selectBySearch 사용
            memberReads = memberMapper.selectBySearch(rq, prq);
            count = memberMapper.countBySearch(rq, prq);
        } else {
            // 일반 조회 → selectAll 사용 (LIMIT/OFFSET 포함)
            memberReads = memberMapper.selectAll(rq, prq);
            count = memberMapper.countAll(rq);
        }
        // [4] 조회 결과 반환
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
    public MemberDTO.Read join(MemberDTO.Join rq) {

        // [1] Entity 생성
        String nickname = StrUtils.createRandomNumString(10, "학생");
        String password = passwordEncoder.encode(rq.getPassword());
        Member member = Member.joinNormalStudent(rq.getEmail(), password, nickname);

        // [2] 이메일 중복 최종 검증
        if (memberRepository.existsByEmailAndProvider(rq.getEmail(), Provider.NORMAL))
            throw new BusinessLogicException(AppStatus.MEMBER_DUPLICATE_EMAIL);

        // [3] 회원 가입 수행 후, 가입된 회원 정보 반환
        MemberDTO.Read readDTO = DTOMapper.toReadDto(memberRepository.save(member));
        rq.setTarget(readDTO.getMemberId(), Entity.MEMBER); // 로그 기록
        return readDTO;
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

        // [1] 닉네임 중복 검증
        memberRepository
                .findByNickname(nickname)
                .ifPresent(m -> {throw new BusinessLogicException(AppStatus.MEMBER_DUPLICATE_NICKNAME);});

        // [2] 검증을 통과한 경우, 기존 회원 정보 조회 후 갱신 수행
         memberRepository
                 .findById(memberId)
                 .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                 .updateNickname(nickname);
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
    public Page.Response<MemberDTO.Read> search(MemberDTO.Search rq, Page.Request prq) {
        log.info("🔍 [SERVICE] 회원 검색 실행: filter={}, keyword={}, role={}, isActive={}",
                rq.getFilter(), rq.getKeyword(), rq.getRole(), rq.getIsActive());


        // [1] MyBatis 매퍼 호출
        List<MemberDTO.Read> members = memberMapper.selectBySearch(rq, prq);

        // [2] 총 카운트 조회
        int count = memberMapper.countBySearch(rq, prq);
        log.info("📘 [DEBUG] page={}, size={}, startPage={}", prq.getPage(), prq.getSize(), prq.getStartPage());
        // [3] 페이징 응답 생성
        return Page.Response.create(members, prq.getPage(), prq.getSize(), count);
    }

    @Override
    public MemberDTO.Read toggleActive(Long memberId) {
        log.info("[TOGGLE] memberId = {}", memberId);
        // [1] 값 제대로 받았는지 검증
        Optional<Member> opt = memberRepository.findById(memberId);
        if (opt.isEmpty()) {
            log.warn("[TOGGLE] 존재하지 않는 회원 ID: {}", memberId);
            return null;
        }

        // [2] MyBatis로 상태 반전 (UPDATE 실행)
        Long updated = memberMapper.toggleActive(memberId);
        if (updated == 0) {
            log.warn("[TOGGLE] 상태 변경 실패: DB 업데이트 0건");
            return null;
        }

        // [3] 변경된 정보 리턴
        return memberRepository
                .findById(memberId)
                .map(DTOMapper::toReadDto)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public byte[] generateMemberListPdf(MemberDTO.Search rq) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // [1] 모든 회원 데이터 조회
            List<MemberDTO.Read> members = memberMapper.selectBySearch(rq, new Page.Request(0, Integer.MAX_VALUE));

            // [2] iText PDF 관련
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);

            document.open();

            // [3] 테이블 관련 설정(포맷, 글씨체, 가운데 정렬 등등)
            BaseFont baseFont = BaseFont.createFont("fonts/malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font infoFont = new Font(baseFont, 11, Font.NORMAL);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font bodyFont = new Font(baseFont, 10, Font.NORMAL);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = LocalDateTime.now().format(formatter);


            // [4] [권한 필터 상태 문자열 구성] 필터링된 정보에 따라 안내 표식 적기
            String filterLabel = switch (rq.getFilter() == null ? "" : rq.getFilter()) {
                case "email" -> "이메일";
                case "nickname" -> "닉네임";
                default -> "전체";
            };
            String roleLabel = switch (rq.getRole() == null ? "" : rq.getRole()) {
                case "USER" -> "학생";
                case "TEACHER" -> "강사";
                case "ADMIN" -> "관리자";
                default -> "전체";
            };

            String activeLabel = switch (rq.getIsActive() == null ? "" : rq.getIsActive()) {
                case "1", "true" -> "활성";
                case "0", "false" -> "비활성";
                default -> "전체";
            };


            String keywordText = (rq.getKeyword() != null && !rq.getKeyword().isBlank())
                    ? rq.getKeyword()
                    : "없음";

            String filterSummary = String.format(
                    "필터: 검색=%s / 권한=%s / 상태=%s / 키워드=%s",
                    filterLabel, roleLabel, activeLabel, keywordText
            );

            document.add(new Paragraph("📋 Study On 회원 목록", titleFont));
            document.add(new Paragraph("생성시각: " + formattedDate, bodyFont));
            document.add(new Paragraph(filterSummary, infoFont));
            document.add(new Paragraph(" " ));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setWidths(new float[]{1f, 3f, 5f, 3f, 2f, 3f});

            String[] headers = {"No", "닉네임", "이메일", "권한", "상태", "가입일"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(
                        new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);  // 좌우 가운데 정렬
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 상하 가운데 정렬
                cell.setPaddingTop(6f);    // 위쪽 여백
                cell.setPaddingBottom(6f); // 아래쪽 여백
                cell.setBackgroundColor(new BaseColor(230, 230, 230));
                table.addCell(cell);
            }

            int i = 1;
            for (MemberDTO.Read m : members) {
                table.addCell(centeredCell(String.valueOf(i++), bodyFont));
                table.addCell(centeredCell(m.getNickname(), bodyFont));
                table.addCell(centeredCell(m.getEmail(), bodyFont));
                table.addCell(centeredCell(m.getRole().getValue(), bodyFont));
                table.addCell(centeredCell(m.getIsActive() ? "활성" : "비활성", bodyFont));
                table.addCell(centeredCell(
                        m.getCdate() != null ? m.getCdate().toLocalDate().toString() : "-", bodyFont
                ));
            }


            document.add(table);
            document.close();

            log.info("✅ [SERVICE] PDF 생성 완료 ({}명)", members.size());
            return out.toByteArray();

        } catch (IOException | DocumentException e) {
            throw new RuntimeException("PDF 생성 실패", e);
        }
    }

    // PDF 전용 메소드 하나(일부러 상속 안받음)
    private PdfPCell centeredCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(4f);
        return cell;
    }
}
