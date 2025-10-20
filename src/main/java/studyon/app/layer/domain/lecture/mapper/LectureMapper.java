package studyon.app.layer.domain.lecture.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.lecture.LectureDTO;

import java.util.List;

/**
 * 강의 페이징용 매퍼
 * @version 1.0
 * @author khj00
 */

@Mapper
@Repository
public interface LectureMapper {
    List<LectureDTO.Read> selectAll(@Param("rq") LectureDTO.Search rq,
                                    @Param("prq") Page.Request prq);

    Integer countAll(@Param("rq") LectureDTO.Search rq);
}
