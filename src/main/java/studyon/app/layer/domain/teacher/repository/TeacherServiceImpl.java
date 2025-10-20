package studyon.app.layer.domain.teacher.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.constant.Msg;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.exception.NotFoundException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.teacher.TeacherDTO;


import java.util.stream.Collectors;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 서비스 구현체
 * @version 1.0
 * @author khj00
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherDTO.Read> findAllTeachers() {
        // [1] 레포지토리에서 모든 선생님 정보 가져오기

        return teacherRepository.findAll().stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO.Read> findTeachersBySubject(Subject subject) {
        // [1] 레포지토리에서 과목별로 선생님 정보 가져오기

        return teacherRepository.findBySubject(subject).stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO.Read getTeacherProfile(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .map(DTOMapper::toReadDTO)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND_MEMBER));
    }


    @Override
    public void updateTeacherProfile(Long teacherId, TeacherDTO.Edit dto) {
        /*
        teacherRepository.findById(teacherId)
                .ifPresentOrElse(
                        teacher -> teacherRepository.save(DTOMapper.toEntity(dto, teacher))
                );

         */
    }


}
