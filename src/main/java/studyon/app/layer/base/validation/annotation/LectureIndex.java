package studyon.app.layer.base.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import studyon.app.layer.base.validation.validator.LectureIndexValidator;

import java.lang.annotation.*;

/**
 * 강의 목차를 검증하기 위한 애노테이션
 * @version 1.0
 * @author kcw97
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = LectureIndexValidator.class)  // Validator 클래스 지정
@Documented
public @interface LectureIndex {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
