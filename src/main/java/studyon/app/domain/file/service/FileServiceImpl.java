package studyon.app.domain.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.exception.domain.NotFoundException;
import studyon.app.common.infra.utils.DTOMapper;
import studyon.app.domain.file.FileDTO;
import studyon.app.domain.file.repository.FileRepository;

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
                .orElseThrow(() -> new NotFoundException("파일이 존재하지 않습니다"));
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
