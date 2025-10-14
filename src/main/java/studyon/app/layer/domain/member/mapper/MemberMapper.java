package studyon.app.layer.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.member.MemberDTO;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {

    List<MemberDTO.Read> selectAll(@Param("rq") MemberDTO.Search rq,
                                   @Param("prq") Page.Request prq);

    Integer countAll(@Param("rq") MemberDTO.Search rq);
}
