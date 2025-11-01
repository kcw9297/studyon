package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import studyon.app.layer.base.validation.annotation.LectureIndex;

import java.util.Collection;
import java.util.Objects;

/**
 * 에디터 작성 내용 검증을 위한 검증 클래스
 * @version 1.0
 * @author kcw97
 */

public class LectureIndexValidator implements ConstraintValidator<LectureIndex, Collection<String>> {

    @Override
    public boolean isValid(Collection<String> value, ConstraintValidatorContext context) {

        // [1] 목차 제목 패턴 (마침표, 언더바, 하이픈, 느낌표, 물음표, 괄호 허용)
        String pattern = "^[가-힣a-zA-Z0-9\\s,._\\-!?()\\[\\]{}]{5,20}$";

        // [2] 기본 메세지 비활성화
        context.disableDefaultConstraintViolation();

        // [3] 검증 수행
        // 목차가 개수가 범위에 맞지 않는 경우
        if (Objects.isNull(value) || value.isEmpty() || value.size() < 2 || value.size() > 5)
            return setErrorMessageAndReturn(context, "2-5개 사이의 강의 목차를 반드시 생성해야 합니다.");

        // 목차 제목이 패턴과 일치하지 않는 경우
        if (value.stream().noneMatch(v -> v.matches(pattern)))
            return setErrorMessageAndReturn(context, "목차 제목은 5-20자 사이의 문자, 허용 특수문자로만 입력해야 합니다.");

        return true;
    }

    private boolean setErrorMessageAndReturn(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
