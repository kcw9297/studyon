package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import studyon.app.common.constant.Msg;
import studyon.app.layer.base.validation.annotation.IntRange;

import java.util.Objects;

/**
 * long 타입 범위 검증을 위한 클래스
 * @version 1.0
 * @author kcw97
 */

public class LongRangeValidator implements ConstraintValidator<IntRange, Long> {

    // 값 범위
    private long min;
    private long max;

    @Override
    public void initialize(IntRange annotation) {
        this.min = annotation.min();
        this.max = annotation.max();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        // [1] 오류 메세지
        String message = Msg.VALIDATOR_LONG_RANGE.formatted(min, max);

        // [2] 기본 메세지 비활성화
        context.disableDefaultConstraintViolation();

        // [3] 검증 수행
        if (Objects.isNull(value) || value < min || value > max) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }

}