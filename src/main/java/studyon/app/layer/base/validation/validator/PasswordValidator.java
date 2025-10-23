package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import studyon.app.layer.base.validation.annotation.Password;

import java.util.Objects;

/**
 * 비밀번호 검증을 위한 검증 클래스
 * @version 1.0
 * @author kcw97
 */

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // [1] 검증 메세지 & 패턴
        String message = "8-20자 사이 공백제외 영문/숫자/특수문자 입력";
        String pattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$)[a-zA-Z\\d@#$%^&+=!]{8,20}$";

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
