package studyon.app.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import studyon.app.common.dto.Page;
import studyon.app.domain.member.MemberDTO;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {

    List<MemberDTO.Read> selectAll(@Param("rq") MemberDTO.Search rq,
                                   @Param("prq") Page.Request prq);

    Integer countAll(@Param("rq") MemberDTO.Search rq);
}
