package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import studyon.app.layer.base.validation.annotation.Name;
import studyon.app.layer.base.validation.annotation.Title;

import java.util.Objects;

/**
 * 이름 검증을 위한 검증 클래스
 * <br>영어, 한글만 받아야 하는 경우 사용 (영어 문자열 혹은 한글 문자열만)
 * @version 1.0
 * @author kcw97
 */

public class NameValidator implements ConstraintValidator<Name, String> {

    // 사용자 오류 메세지
    private String message;

    @Override
    public void initialize(Name annotation) {

        // 사용자 입력 오류 메세지
        String message = annotation.message();
        this.message = Objects.nonNull(message) && !message.isBlank() ?
                message :
                "2-15자 사이의 영어/한글 성함을 작성해 주세요.";

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // [1] 패턴
        String englishPattern = "^[a-zA-Z]{2,15}$";
        String koreanNamePattern = "^[가-힣]{2,15}$";

        // [2] 기본 메세지 비활성화
        context.disableDefaultConstraintViolation();

        // [3] 검증 수행
        if (Objects.isNull(value) || (!value.matches(englishPattern) && !value.matches(koreanNamePattern))) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }
}
