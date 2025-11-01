package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import studyon.app.layer.base.validation.annotation.Text;
import studyon.app.layer.base.validation.annotation.Title;

import java.util.Objects;

/**
 * 긴 문자열 검증을 위한 검증 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
public class TextValidator implements ConstraintValidator<Text, String> {

    // 사용자 오류 메세지
    private String message;
    private int min;
    private int max;

    @Override
    public void initialize(Text annotation) {

        // 최소, 최대 길이
        this.min = Math.max(annotation.min(), 0);
        this.max = Math.max(annotation.max(), 0);

        // 사용자 입력 오류 메세지
        this.message = annotation.message();
        if (Objects.isNull(message) || message.isBlank()) {
            if (Objects.equals(min, 0)) this.message = "최대 %d자 이내 내용을 입력해야 합니다.".formatted(max);
            else this.message = "%d-%d자 사이 이내 내용을 입력해야 합니다.".formatted(min, max);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // [1] 패턴
        String pattern = "^[가-힣a-zA-Z0-9\\s\\r\\n]{%d,%d}$".formatted(min, max);

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
