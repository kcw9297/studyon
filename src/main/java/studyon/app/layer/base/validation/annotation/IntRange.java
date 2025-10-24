package studyon.app.layer.base.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import studyon.app.layer.base.validation.validator.IntRangeValidator;

import java.lang.annotation.*;

/**
 * int 타입 범위 검증을 적용할 애노테이션
 * @version 1.0
 * @author kcw97
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IntRangeValidator.class)
@Documented
public @interface IntRange {

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // 정수 최소값 (미지정 시 무제한)
    int min() default Integer.MIN_VALUE;

    // 정수 최대값 (미지정 시 무제한)
    int max() default Integer.MAX_VALUE;
}
