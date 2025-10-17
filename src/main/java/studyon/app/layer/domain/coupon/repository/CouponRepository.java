package studyon.app.layer.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.coupon.Coupon;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */


/**
 * 쿠폰 레포지토리
 * @version 1.0
 * @author khj00
 */

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
