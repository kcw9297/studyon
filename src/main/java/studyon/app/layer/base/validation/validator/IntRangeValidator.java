package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import studyon.app.common.constant.Msg;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.validation.annotation.IntRange;

import java.util.Objects;

/**
 * int 타입 범위 검증을 위한 클래스
 * @version 1.0
 * @author kcw97
 */

public class IntRangeValidator implements ConstraintValidator<IntRange, Integer> {

    // 사용자 오류 메세지
    private String message;
    private int min;
    private int max;

    @Override
    public void initialize(IntRange annotation) {

        // 최소, 최대 값 범위
        this.min = Math.max(annotation.min(), 0);
        this.max = Math.max(annotation.max(), 0);

        // 숫자 포메팅
        String minStr = StrUtils.formatKoreaNumber(min);
        String maxStr = StrUtils.formatKoreaNumber(max);

        // 사용자 입력 오류 메세지
        this.message = annotation.message();
        if (Objects.isNull(message) || message.isBlank()) {
            if (Objects.equals(min, 0)) this.message = "최대 %s 이내의 양수를 입력해야 합니다.".formatted(maxStr);
            else this.message = "%s-%s 이내의 양수를 입력해야 합니다.".formatted(minStr, maxStr);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        // [1] 기본 메세지 비활성화
        context.disableDefaultConstraintViolation();

        // [2] 검증 수행
        if (Objects.isNull(value) || value < min || value > max) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }

}