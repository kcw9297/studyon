package studyon.app.layer.base.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import studyon.app.layer.base.validation.validator.NumberStringValidator;

import java.lang.annotation.*;

/**
 * 숫자 문자열 검증
 * @version 1.0
 * @author kcw97
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = NumberStringValidator.class)  // Validator 클래스 지정
@Documented
public @interface NumberString {
    String message() default "";
    int min() default 0;
    int max() default 255;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
