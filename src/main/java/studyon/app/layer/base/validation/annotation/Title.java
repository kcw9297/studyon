package studyon.app.layer.base.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import studyon.app.layer.base.validation.validator.TitleValidator;

import java.lang.annotation.*;

/**
 * 제목 검증을 적용할 애노테이션 (제목을 사용하는 곳에서 보편적으로 사용)
 * @version 1.0
 * @author kcw97
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = TitleValidator.class)  // Validator 클래스 지정
@Documented
public @interface Title {
    String message() default "";
    int min() default 0;
    int max() default 255;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
