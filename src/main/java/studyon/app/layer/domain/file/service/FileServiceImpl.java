package studyon.app.layer.domain.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.constant.StatusCode;
import studyon.app.layer.base.exception.BusinessException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    @Transactional(readOnly = true)
    public FileDTO.Read read(Long fileId) {
        return fileRepository
                .findById(fileId)
                .map(DTOMapper::toReadDTO)
                .orElseThrow(() -> new BusinessException("파일이 존재하지 않습니다", StatusCode.FILE_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileDTO.Read> readList() {
        return fileRepository
                .findAll()
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }

}
