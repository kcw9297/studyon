package studyon.app.layer.domain.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;
import studyon.app.layer.domain.notice.Notice;
import studyon.app.layer.domain.notice.NoticeDTO;
import studyon.app.layer.domain.notice.repository.NoticeRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final FileRepository fileRepository;
    private final FileManager fileManager;


    @Override
    @Transactional(readOnly = true)
    public List<NoticeDTO.Read> readAll() {
        return noticeRepository
                .findAllWithFile()
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }


    @Override
    public List<NoticeDTO.Read> readAllActivate() {
        return noticeRepository
                .findAllWithFileByIsActivate(true) // 활성 상태만 조회
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }


    @Override
    public void editTitle(Integer index, String title) {
        noticeRepository
                .findByIdx(index)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND))
                .updateTitle(title);
    }


    @Override
    public void editNoticeImage(Integer index, MultipartFile noticeImageFile) {

        // [1] 공지사항 조회
        Notice notice = noticeRepository
                .findByIdx(index)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND));


        // [2] 프로필 이미지 존재여부 비교
        File noticeImage = notice.getNoticeImage();

        // [2-1] 만약 존재하지 않으면 새롭게 생성
        if (Objects.isNull(noticeImage)) {

            // 업로드 요청 DTO 생성
            FileDTO.Upload uploadDTO =
                    DTOMapper.toUploadDTO(noticeImageFile, notice.getNoticeId(), Entity.NOTICE, FileType.THUMBNAIL);

            // DB 내 파일정보 생성
            notice.updateNoticeImage(fileRepository.save(DTOMapper.toEntity(uploadDTO)));

            // 물리적 저장 수행
            fileManager.upload(noticeImageFile, uploadDTO.getStoreName(), uploadDTO.getEntity().getName());


            // [2-2] 새롭게 존재하면 파일 정보만 갱신
        } else {

            // 바뀌는 파일 원래 정보만 변경 (저장 파일명은 바꾸지 않음)
            noticeImage.update(
                    noticeImageFile.getOriginalFilename(),
                    StrUtils.extractFileExt(noticeImageFile.getOriginalFilename()),
                    noticeImageFile.getSize()
            );

            // 물리적 파일 덮어쓰기 수행 (같은 파일명으로 재업로드)
            fileManager.upload(noticeImageFile, noticeImage.getStoreName(), noticeImage.getEntity().getName());
        }

    }

    @Override
    public void activate(Integer index) {

        // [1] 공지사항 조회
        Notice notice = noticeRepository
                .findByIdx(index)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND));

        // [2] 제목이 빈 칸이거나, 등록 이미지가 존재하지 않으면 예외 처리
        if (Objects.isNull(notice.getNoticeImage()) || notice.getTitle().isBlank())
            throw new BusinessLogicException(AppStatus.NOTICE_NOT_EXIST_TITLE_AND_IMAGE);

        // [3] 공지사항을 활성 상태로 변경 수행
        notice.activate();
    }

    @Override
    public void inactivate(Integer index) {
        noticeRepository
                .findByIdx(index)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND))
                .inactivate();
    }

    @Override
    public void initialize(Integer index) {

        // [1] 공지사항 조회
        Notice notice = noticeRepository
                .findByIdx(index)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND));

        // [2] 파일 정보 조회 후 삭제 & 공지 엔티티 갱신
        File noticeImage = notice.getNoticeImage();
        notice.initialize(); // 초기화 후에는 파일 정보가 없으므로 먼저 파일 조회 후 초기화 수행

        // 존재하는 경우에만 파일 삭제
        if (Objects.nonNull(noticeImage)) {
            fileRepository.delete(noticeImage);
            fileManager.remove(noticeImage.getStoreName(), Entity.NOTICE.getName());
        }
    }

}
