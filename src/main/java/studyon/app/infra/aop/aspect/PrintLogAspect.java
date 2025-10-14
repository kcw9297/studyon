package studyon.app.infra.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 프로젝트 공용 로그 처리 Aspect
 * @version 1.0
 * @author kcw97
 */
@Order(3)
@Slf4j
@Aspect
@Component
public class PrintLogAspect {

    /*
         성공적으로 메소드 종료 시 로그 출력
         domain 내 클래스 범위 한정
     */

    @Around("studyon.app.infra.aop.Pointcuts.basePackSvcAnnoExReadMeth()")
    public Object printSuccessLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // [1] 로직 시작 시점 기록
        long startTime = System.nanoTime();

        // [2] 로직 수행 후, 종료 시점 기록
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();

        // [3] 성공 로그 기록
        doPrintSuccess(joinPoint, endTime-startTime);
        return result;
    }

    /**
     * <pre>출력 예시<br>
     * [LoginController::login] 수행 완료 (0.133 ms)</pre>
     */
    private void doPrintSuccess(JoinPoint joinPoint, long cost) {

        try {
            // [1] 로그 필요정보 추출
            Signature signature = joinPoint.getSignature();
            String className = signature.getDeclaringType().getSimpleName();
            String methodName = signature.getName();

            // [2] 로그 출력
            log.info("[{}::{}] {}",
                    className,
                    methodName,
                    "수행 완료 (%.3f ms)".formatted(cost / 1_000_000_000.0)
            );
        } catch (Exception e) {
            log.error("[PrintLogAspect::printSuccessLog] 성공 로그 출력 실패! 확인 요망.", e);
        }
    }


    /*
        로직 실패로 인한 예외 반환 시 출력 로그
        예외 발생 근원지에서만 출력 (프로젝트 내 모든 클래스 범위)
     */
    @AfterThrowing(
            pointcut = "studyon.app.infra.aop.Pointcuts.basePack()",
            throwing = "e"
    )
    public void printErrorLog(JoinPoint joinPoint, Exception e) {

        // [1] 검증 - 예외가 발생한 최초 지점 검증
        StackTraceElement origin = findAppOrigin(e);
        Signature signature = joinPoint.getSignature();

        // [2] 현재 JoinPoint가 최초 발생 지점과 일치하는 경우에만 로그 출력
        boolean isOriginClass = Objects.equals(signature.getDeclaringTypeName(), origin.getClassName());
        boolean isOriginMethod = Objects.equals(signature.getName(), origin.getMethodName());
        if (isOriginClass && isOriginMethod) doPrintError(origin, e);
    }



    /**
     * <pre>출력 예시<br>
     * ┌─ ERROR DETAILS ──────────────────────────────────────────────────
     * │ Location: studyon.app.common.infra.file.LocalFileManager
     * │ Type:     upload (Line 38)
     * │ Cause:    저장할 파일이 존재하지 않습니다!
     * └──────────────────────────────────────────────────────────────────</pre>
     */
    private void doPrintError(StackTraceElement origin, Exception e) {

        // [1] 로그 필요정보 추출
        String className = origin.getClassName();
        String methodName = origin.getMethodName();
        int lineNumber = origin.getLineNumber();
        String exClassName = e.getClass().getSimpleName();
        String exMessage = e.getMessage();

        // [2] Log DTO 생성 및 로그 출력 수행
        log.error("""
        
        ┌─ ERROR DETAILS ───────────────────────────────────────────────────────────────
        │ Location:     {}
        │ method:       {} (Line {})
        │ Type:         {}
        │ Cause:        {}
        └───────────────────────────────────────────────────────────────────────────────
        """,
                className,
                methodName,
                lineNumber,
                exClassName,
                exMessage
        );
    }


    private StackTraceElement findAppOrigin(Throwable e) {

        // [1] 예외 근원 탐색
        Throwable root = getRootCause(e);

        // [2] 우리 프로젝트에서 발생한 예외를 찾으면 반환
        for (StackTraceElement element : root.getStackTrace()) {
            if (element.getClassName().startsWith("studyon.app")) {
                return element;
            }
        }
        // [3] 못 찯은 경우 root cause의 첫 번째 스택 반환
        return root.getStackTrace()[0];
    }


    // 가장 안쪽 실제 원인까지 탐색
    private Throwable getRootCause(Throwable e) {
        Throwable cause = e;
        while (Objects.nonNull(cause.getCause())) {
            cause = cause.getCause();
        }
        return cause;
    }



}
