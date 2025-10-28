package studyon.app.layer.base.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import studyon.app.common.constant.Msg;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.validation.annotation.EditorContentRange;

import java.util.Objects;

/**
 * 에디터 작성 내용 검증을 위한 검증 클래스
 * @version 1.0
 * @author kcw97
 */

public class EditorRangeValidator implements ConstraintValidator<EditorContentRange, String> {

    // 사용자 오류 메세지
    private String message;

    @Override
    public void initialize(EditorContentRange annotation) {
        // 사용자 입력 오류 메세지
        String message = annotation.message();
        this.message = Objects.isNull(message) || message.isBlank() ?
                "2000자 이하의 내용 작성" : message;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // [1] 기본 메세지 비활성화
        context.disableDefaultConstraintViolation();

        // [2] html 태그 제거 후 검증 수행
        String pureContext = StrUtils.removeHtmlTags(value);

        if (Objects.isNull(pureContext) || pureContext.length() > 2000) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }
}
