package studyon.app.layer.domain.coupon.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.layer.domain.coupon.Coupon;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 쿠폰 레포지토리 테스트
 * @version 1.0
 * @author khj00
 */

@SpringBootTest
@Transactional
class CouponRepositoryTest {
    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰 등록 및 조회 테스트")
    void saveAndFindCoupon() {
        // given
        Coupon coupon = Coupon.builder()
                .name("WELCOME10")
                .discountPrice(1000.0)
                .discountRate(10.0)
                .validFrom(LocalDateTime.now())
                .validUntil(LocalDateTime.now().plusDays(7))
                .availableScope("전체 강의")
                .discountLimit(7000.0)
                .build();

        // when

        Coupon savedCoupon = couponRepository.save(coupon);

        // then

        assertThat(savedCoupon.getCouponId()).isNotNull();

        Optional<Coupon> found = couponRepository.findById(savedCoupon.getCouponId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("WELCOME10");

        System.out.println("저장된 쿠폰: " + found.get());

    }
}