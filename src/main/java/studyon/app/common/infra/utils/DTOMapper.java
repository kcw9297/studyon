package studyon.app.common.infra.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Entity;
import studyon.app.domain.file.File;
import studyon.app.domain.file.FileDTO;
import studyon.app.domain.log.Log;
import studyon.app.domain.log.LogDTO;
import studyon.app.domain.member.Member;
import studyon.app.domain.member.MemberDTO;

import java.util.Objects;
import java.util.UUID;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 특정 객체를 DTO 혹은 Entity 으로의 매핑 로직 처리
 * @version 1.0
 * @author kcw97
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DTOMapper {

    public static Member toEntity(MemberDTO.Join dto) {
        return Member.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .providerId(dto.getProviderId())
                .socialProvider(dto.getSocialProvider())
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

    public static MemberDTO.Read toReadDTO(Member entity) {
        return MemberDTO.Read.builder()
                .targetId(entity.getMemberId())
                .targetEntity(Entity.MEMBER)
                .memberId(entity.getMemberId())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .lastLoginAt(entity.getLastLoginAt())
                .cdate(entity.getCdate())
                .socialProvider(entity.getSocialProvider())
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

}
