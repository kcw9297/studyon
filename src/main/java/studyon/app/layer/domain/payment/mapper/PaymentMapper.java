package studyon.app.layer.domain.payment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.payment.PaymentDTO;

import java.util.List;

/**
 * 결제 페이징용 매퍼
 * @version 1.0
 * @author kcw97
 */

@Mapper
@Repository
public interface PaymentMapper {

    List<PaymentDTO.Read> selectAll(@Param("rq") PaymentDTO.Search rq,
                                   @Param("prq") Page.Request prq);

    Integer countAll(@Param("rq") PaymentDTO.Search rq);
}
