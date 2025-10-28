package studyon.app.layer.domain.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.member.MemberDTO;

import java.util.List;

/**
 * 멤버 페이징용 매퍼
 * @version 1.0
 * @author kcw97
 */

@Mapper
@Repository
public interface MemberMapper {

    List<MemberDTO.Read> selectAll(@Param("rq") MemberDTO.Search rq,
                                   @Param("prq") Page.Request prq);

    Integer countAll(@Param("rq") MemberDTO.Search rq);

    // 검색 전용 메소드들
    List<MemberDTO.Read> selectBySearch(@Param("rq") MemberDTO.Search rq,
                                        @Param("prq") Page.Request prq);
    Integer countBySearch(@Param("rq") MemberDTO.Search rq,
                          @Param("prq") Page.Request prq);

    Integer toggleActive(@Param("memberId") Long memberId);
}
