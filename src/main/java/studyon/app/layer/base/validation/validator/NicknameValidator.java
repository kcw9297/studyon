package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import studyon.app.layer.base.validation.annotation.Nickname;

import java.util.Objects;

/**
 * 닉네임 검증을 위한 검증 클래스
 * @version 1.0
 * @author kcw97
 */

public class NicknameValidator implements ConstraintValidator<Nickname, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // [1] 검증 메세지 & 패턴
        String message = "5-12자 사이 한글/영문/숫자 입력";
        String pattern = "^[가-힣a-zA-Z0-9]{5,12}$";

        // [2] 기본 메세지 비활성화
        context.disableDefaultConstraintViolation();

        // [3] 검증 수행
        if (Objects.isNull(value) || value.isBlank() || !value.matches(pattern)) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }
}
