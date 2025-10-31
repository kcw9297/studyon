package studyon.app.layer.domain.banner.service;

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
import studyon.app.layer.domain.banner.Banner;
import studyon.app.layer.domain.banner.BannerDTO;
import studyon.app.layer.domain.banner.repository.BannerRepository;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;

import java.util.List;
import java.util.Objects;

/**
 * 배너 서비스 구현체
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final FileRepository fileRepository;
    private final FileManager fileManager;


    @Override
    @Transactional(readOnly = true)
    public List<BannerDTO.Read> readAll() {
        return bannerRepository
                .findAllWithBannerImage()
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }


    @Override
    public List<BannerDTO.Read> readAllActivate() {
        return bannerRepository
                .findAllWithBannerImageByIsActivate(true) // 활성 상태만 조회
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }


    @Override
    public void editTitle(Integer idx, String title) {
        bannerRepository
                .findByIdx(idx)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.BANNER_NOT_FOUND))
                .updateTitle(title);
    }


    @Override
    public void editNoticeImage(Integer idx, MultipartFile bannerImageFile) {

        // [1] 공지사항 조회
        Banner banner = bannerRepository
                .findByIdx(idx)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.BANNER_NOT_FOUND));


        // [2] 프로필 이미지 존재여부 비교
        File bannerImage = banner.getBannerImage();

        // [2-1] 만약 존재하지 않으면 새롭게 생성
        if (Objects.isNull(bannerImage)) {

            // 업로드 요청 DTO 생성
            FileDTO.Upload uploadDTO =
                    DTOMapper.toUploadDTO(bannerImageFile, banner.getBannerId(), Entity.NOTICE, FileType.THUMBNAIL);

            // DB 내 파일정보 생성
            banner.updateBannerImage(fileRepository.save(DTOMapper.toEntity(uploadDTO)));

            // 물리적 저장 수행
            fileManager.upload(bannerImageFile, uploadDTO.getStoreName(), uploadDTO.getEntity().getName());


            // [2-2] 새롭게 존재하면 파일 정보만 갱신
        } else {

            // 바뀌는 파일 원래 정보만 변경 (저장 파일명은 바꾸지 않음)
            bannerImage.update(
                    bannerImageFile.getOriginalFilename(),
                    StrUtils.extractFileExt(bannerImageFile.getOriginalFilename()),
                    bannerImageFile.getSize()
            );

            // 물리적 파일 덮어쓰기 수행 (같은 파일명으로 재업로드)
            fileManager.upload(bannerImageFile, bannerImage.getStoreName(), bannerImage.getEntity().getName());
        }

    }

    @Override
    public void activate(Integer idx) {

        // [1] 공지사항 조회
        Banner banner = bannerRepository
                .findByIdx(idx)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.BANNER_NOT_FOUND));

        // [2] 제목이 빈 칸이거나, 등록 이미지가 존재하지 않으면 예외 처리
        if (Objects.isNull(banner.getBannerImage()) || banner.getTitle().isBlank())
            throw new BusinessLogicException(AppStatus.BANNER_NOT_EXIST_TITLE_AND_IMAGE);

        // [3] 공지사항을 활성 상태로 변경 수행
        banner.activate();
    }

    @Override
    public void inactivate(Integer idx) {
        bannerRepository
                .findByIdx(idx)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.BANNER_NOT_FOUND))
                .inactivate();
    }

    @Override
    public void initialize(Integer idx) {

        // [1] 공지사항 조회
        Banner banner = bannerRepository
                .findByIdx(idx)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.BANNER_NOT_FOUND));

        // [2] 파일 정보 조회 후 삭제 & 공지 엔티티 갱신
        File bannerImage = banner.getBannerImage();
        banner.initialize(); // 초기화 후에는 파일 정보가 없으므로 먼저 파일 조회 후 초기화 수행

        // 존재하는 경우에만 파일 삭제
        if (Objects.nonNull(bannerImage)) {
            fileRepository.delete(bannerImage);
            fileManager.remove(bannerImage.getStoreName(), Entity.BANNER.getName());
        }
    }

}
