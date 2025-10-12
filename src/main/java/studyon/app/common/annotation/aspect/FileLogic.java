package studyon.app.common.annotation.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 파일 로직 수행을 표시하기 위한 애노테이션<br>
 * 자체에는 특별한 기능이 없음 (AOP를 이용하여 적용)
 * @version 1.0
 * @author kcw97
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FileLogic {
}
