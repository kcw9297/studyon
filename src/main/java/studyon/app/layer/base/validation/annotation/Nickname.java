package studyon.app.layer.base.validation.annotation;

import jakarta.validation.Constraint;
import studyon.app.layer.base.validation.validator.NicknameValidator;

import java.lang.annotation.*;

/**
 * 닉네임 검증을 적용할 애노테이션
 * @version 1.0
 * @author kcw97
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NicknameValidator.class)  // Validator 클래스 지정
@Documented
public @interface Nickname {
    String message() default "올바른 형식의 닉네임을 작성해 주세요";
}
