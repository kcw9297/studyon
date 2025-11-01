package studyon.app.layer.domain.lecture_video.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.lecture_index.repository.LectureIndexRepository;
import studyon.app.layer.domain.lecture_video.LectureVideo;
import studyon.app.layer.domain.lecture_video.LectureVideoDTO;
import studyon.app.layer.domain.lecture_video.repository.LectureVideoRepository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LectureVideoServiceImpl implements LectureVideoService {

    private final LectureVideoRepository lectureVideoRepository;
    private final LectureIndexRepository lectureIndexRepository;
    private final FileManager fileManager;
    private final FileRepository fileRepository;

    @Override
    public List<LectureVideoDTO.Read> getVideosForMemberLecture(Long memberId, Long indexId) {
        log.info("🎬 [VIDEO LIST] memberId={}, indexId={}", memberId, indexId);

        // ✅ 목차(indexId) 기준으로 영상 조회
        List<LectureVideo> videos = lectureVideoRepository.findAllByLectureIndex_LectureIndexId(indexId);

        return videos.stream()
                .map(v -> LectureVideoDTO.Read.builder()
                        .lectureVideoId(v.getLectureVideoId())
                        .title(v.getTitle())
                        .seq(v.getSeq())
                        .duration(v.getDuration())
                        .videoUrl(v.getVideoUrl())
                        .lectureIndexId(v.getLectureIndex().getLectureIndexId())
                        .createdAt(v.getCreatedAt())
                        .updatedAt(v.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void uploadVideo(Long indexId, MultipartFile file){
        LectureIndex lectureIndex = lectureIndexRepository.findById(indexId).orElseThrow(() -> new BusinessLogicException(AppStatus.FILE_NOT_FOUND));
        FileDTO.Upload uploadDTO = DTOMapper.toUploadDTO(
                file,
                indexId,
                Entity.LECTURE_VIDEO,   // 📌 enum: LECTURE_VIDEO
                FileType.VIDEO          // 📌 enum: VIDEO
        );


        LectureVideo lectureVideo = LectureVideo.builder()
                .title(file.getOriginalFilename())
                .seq(1)                            // 일단 기본값 (추후 영상 순서 로직에서 수정 가능)
                .duration(0)                       // 일단 기본값 (추후 비디오 메타데이터에서 업데이트)
                .videoUrl(uploadDTO.getFilePath()) // 물리 경로 or 접근 URL
                .lectureIndex(lectureIndex)
                .build();
        File savedFile = fileRepository.save(DTOMapper.toEntity(uploadDTO));
        lectureVideo.updateVideoFile(savedFile);
        lectureVideoRepository.save(lectureVideo);

        //추가 video 갯수갱신
        Long lectureId = lectureIndex.getLecture().getLectureId();
        long count = lectureVideoRepository.countByLectureIndex_Lecture_LectureId(lectureId);

        Lecture lecture = lectureIndex.getLecture();
        lecture.setVideoCount(count);
        log.info("🎞 영상 개수 갱신 완료 → lectureId={}, count={}", lectureId, count);


        //실제 파일 물리 업로드
        fileManager.upload(
                file,
                uploadDTO.getStoreName(),
                uploadDTO.getEntity().getName()
        );

    }

    @Override
    public List<LectureVideoDTO.Read> getVideosByIndexId(Long indexId) {
        // 🎯 1️⃣ 목차(lecture_index_id) 기준으로 조회
        List<LectureVideo> videos = lectureVideoRepository.findAllByLectureIndexId(indexId);

        // 🎯 2️⃣ DTO 변환
        return videos.stream()
                .map(v -> LectureVideoDTO.Read.builder()
                        .lectureVideoId(v.getLectureVideoId())
                        .title(v.getTitle())
                        .seq(v.getSeq())
                        .duration(v.getDuration())
                        .videoUrl(v.getVideoUrl())
                        .lectureIndexId(v.getLectureIndex().getLectureIndexId())
                        .createdAt(v.getCreatedAt())
                        .updatedAt(v.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
