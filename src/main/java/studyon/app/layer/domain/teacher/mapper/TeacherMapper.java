package studyon.app.layer.domain.teacher.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;

/**
 * 관리자 페이지 강사 관리 페이징용 매퍼
 * @version 1.0
 * @author khj00
 */

@Mapper
@Repository
public interface TeacherMapper {
    List<TeacherDTO.Read> findBySearch(@Param("rq") TeacherDTO.Search rq,
                                       @Param("prq") Page.Request prq);

    Integer countBySearch(@Param("rq") TeacherDTO.Search rq);

    List<TeacherDTO.Read> findAll();
}
