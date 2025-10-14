package studyon.app.layer.domain.log.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.log.LogDTO;
import studyon.app.layer.domain.log.repository.LogRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 독립적 트랜잭션에서 동작
    public void generate(LogDTO.Generate request) {
        logRepository.save(DTOMapper.toEntity(request));
    }
}
