package studyon.app.layer.base.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import studyon.app.layer.base.validation.validator.TextValidator;

import java.lang.annotation.*;

/**
 * 긴 문자열(Textarea) 검증에 사용하는 애노테이션
 * @version 1.0
 * @author kcw97
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = TextValidator.class)  // Validator 클래스 지정
@Documented
public @interface Text {
    String message() default "";
    int min() default 0;
    int max() default 255;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
