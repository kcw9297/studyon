package studyon.app.layer.domain.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;
import studyon.app.layer.domain.notice.Notice;
import studyon.app.layer.domain.notice.NoticeDTO;
import studyon.app.layer.domain.notice.mapper.NoticeMapper;
import studyon.app.layer.domain.notice.repository.NoticeRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;
    private final FileRepository fileRepository;
    private final FileManager fileManager;


    @Override
    public Page.Response<NoticeDTO.Read> readPagedList(NoticeDTO.Search rq, Page.Request prq) {

        // [1] 페이징 & 카운팅 쿼리 수행
        List<NoticeDTO.Read> pagedList = noticeMapper.selectAll(rq, prq);
        Integer count = noticeMapper.countAll(rq);

        // [2] 페이징 정보 계산 후 반환
        return Page.Response.create(pagedList, prq.getPage(), count, prq.getSize());
    }


    @Override
    public NoticeDTO.Read read(Long noticeId) {
        return noticeRepository
                .findByNoticeIdWithFile(noticeId)
                .map(entity -> DTOMapper.toReadDTO(entity, DTOMapper.toReadDTO(entity.getNoticeImage())))
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND));
    }


    @Override
    public NoticeDTO.Read write(NoticeDTO.Write rq) {

        // [1] Entity 변환
        Notice entity = DTOMapper.toEntity(rq);

        // [2] 저장 후, 생성된 공지정보 DTO 반환
        return DTOMapper.toReadDTO(noticeRepository.save(entity));
    }


    @Override
    public void edit(NoticeDTO.Edit rq) {
        noticeRepository
                .findById(rq.getNoticeId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND))
                .update(rq.getTitle(), rq.getContent(), rq.getNoticeType(), rq.getStartedAt(), rq.getEndedAt());
    }


    @Override
    public void editNoticeImage(Long noticeId, MultipartFile noticeImageFile) {

        // [1] 공지사항 조회
        Notice notice = noticeRepository
                .findById(noticeId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND));


        // [2] 프로필 이미지 존재여부 비교
        File noticeImage = notice.getNoticeImage();

        // [2-1] 만약 존재하지 않으면 새롭게 생성
        if (Objects.isNull(noticeImage)) {

            // 업로드 요청 DTO 생성
            FileDTO.Upload uploadDTO =
                    DTOMapper.toUploadDTO(noticeImageFile, noticeId, Entity.NOTICE, FileType.THUMBNAIL);

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
    public void activate(Long noticeId) {
        noticeRepository
                .findById(noticeId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND))
                .activate();
    }

    @Override
    public void inactivate(Long noticeId) {
        noticeRepository
                .findById(noticeId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.NOTICE_NOT_FOUND))
                .inactivate();
    }

    @Override
    public void remove(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    @Override
    public void removeAll(Collection<Long> noticeIds) {
        noticeRepository.deleteAllById(noticeIds);
    }
}
