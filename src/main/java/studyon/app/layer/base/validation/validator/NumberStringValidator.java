package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import studyon.app.layer.base.validation.annotation.Nickname;
import studyon.app.layer.base.validation.annotation.NumberString;

import java.util.Objects;

/**
 * 닉네임 검증을 위한 검증 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
public class NumberStringValidator implements ConstraintValidator<NumberString, String> {

    // 사용자 오류 메세지
    private String message;
    private int min;
    private int max;

    @Override
    public void initialize(NumberString annotation) {

        // 최소, 최대 길이
        this.min = Math.max(annotation.min(), 0);
        this.max = Math.max(annotation.max(), 0);

        // 사용자 입력 오류 메세지
        this.message = annotation.message();
        if (Objects.isNull(message) || message.isBlank()) {
            if (Objects.equals(min, 0)) this.message = "최대 %d자 이내 숫자를 입력해야 합니다.".formatted(max);
            else this.message = "%d-%d자 사이 숫자를 입력해야 합니다.".formatted(min, max);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // [1] 패턴
        String pattern = "^[0-9]{%d,%d}$".formatted(min, max);

        // [2] 기본 메세지 비활성화
        context.disableDefaultConstraintViolation();

        // [3] 검증 수행
        if (Objects.isNull(value) || !value.matches(pattern)) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }
}
