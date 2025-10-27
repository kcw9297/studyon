package studyon.app.layer.base.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import studyon.app.layer.base.validation.validator.EmailValidator;

import java.lang.annotation.*;

/**
 * 이메일 검증을 적용할 애노테이션
 * @version 1.0
 * @author kcw97
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = EmailValidator.class)  // Validator 클래스 지정
@Documented
public @interface Email {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
