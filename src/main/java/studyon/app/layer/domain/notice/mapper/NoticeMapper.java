package studyon.app.layer.domain.notice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.notice.NoticeDTO;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {

    List<NoticeDTO.Read> selectAll(@Param("rq") NoticeDTO.Search eq,
                                   @Param("prq") Page.Request prq);

    Integer countAll(@Param("rq") NoticeDTO.Search rq);
}
