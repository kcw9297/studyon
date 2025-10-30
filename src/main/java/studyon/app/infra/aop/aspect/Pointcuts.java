package studyon.app.infra.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * Spring AOP 적용을 위한 Pointcut 식을 관리하는 클래스
 * @version 1.0
 * @author kcw97
 */
@Aspect
class Pointcuts {

    /*
     * Pointcut - Annotation 적용 클래스
     */

    // @Controller 적용 클래스
    @Pointcut("(@within(org.springframework.stereotype.Controller) || " +
            "@target(org.springframework.stereotype.Controller))")
    public void ctlAnno() {}

    // @RestController 적용 클래스
    @Pointcut("(@within(org.springframework.web.bind.annotation.RestController) || " +
            "@target(org.springframework.web.bind.annotation.RestController))")
    public void restCtlAnno() {}

    // @Service 적용 클래스
    @Pointcut("(@within(org.springframework.stereotype.Service) || " +
            "@target(org.springframework.stereotype.Service))")
    public void svcAnno() {}

    // @Repository 적용 클래스
    @Pointcut("@within(org.springframework.stereotype.Repository)" +
            "@target(org.springframework.stereotype.Repository)")
    public void repoAnno() {}

    /*
     * Pointcut - Annotation 적용 메소드
     */

    // @RedissonLock 적용 메소드
    @Pointcut("@annotation(studyon.app.infra.aop.annotation.RedissonLock)")
    public void redissonLockAnno() {}

    /*
     * Pointcut - package 범위
     * 보안, 로그 관련영역은 범위에서 제외
     */

    @Pointcut("execution(* studyon.app.infra.security..*(..))")
    public void securityPack() {}

    // studyon.app.layer.domain 내 패키지 (기본 패키지 주소)
    @Pointcut("execution(* studyon.app.layer.domain.log..*(..))")
    public void logPack() {}

    // studyon.app 내 패키지 (기본 패키지 주소)
    @Pointcut("execution(* studyon.app..*(..)) && !securityPack() && !logPack()")
    public void basePack() {}

    // studyon.layer.app 내 패키지 (기본 패키지 주소)
    @Pointcut("execution(* studyon.app.layer..*(..)) !securityPack() && !logPack()")
    public void layerPack() {}

    // studyon.app.layer.domain 내 패키지 (기본 패키지 주소)
    @Pointcut("execution(* studyon.app.layer.domain..*(..)) !securityPack() && !logPack()")
    public void domainPack() {}



    /*
     * Pointcut - 클래스 이름
     */

    // "Admin~" 가 앞에 오는 클래스
    @Pointcut("execution(* *..Admin*.*(..))")
    public void adminCls() {}

    // "File~" 가 앞에 오는 클래스
    @Pointcut("execution(* *..File*.*(..))")
    public void fileCls() {}

    /*
     * Pointcut - 메소드 이름
     */

    // "get" 으로 시작하는 메소드
    @Pointcut("execution(* *..read*(..))")
    public void readMeth() {}

    // "write" 으로 시작하는 메소드
    @Pointcut("execution(* *..write*(..))")
    public void writeMeth() {}

    // "edit" 으로 시작하는 메소드
    @Pointcut("execution(* *..edit*(..))")
    public void editMeth() {}

    // "deactivate" 으로 시작하는 메소드
    @Pointcut("execution(* *..inactivate*(..))")
    public void inactivateMeth() {}

    // "initialize" 으로 시작하는 메소드
    @Pointcut("execution(* *..remove*(..))")
    public void removeMeth() {}

    // "join" 으로 시작하는 메소드
    @Pointcut("execution(* *..join*(..))")
    public void joinMeth() {}

    // "withdraw" 으로 시작하는 메소드
    @Pointcut("execution(* *..withdraw*(..))")
    public void withdrawMeth() {}

    // "pay" 으로 시작하는 메소드
    @Pointcut("execution(* *..pay*(..))")
    public void payMeth() {}

    // "refund" 으로 시작하는 메소드
    @Pointcut("execution(* *..refund*(..))")
    public void refundMeth() {}

    // "upload" 으로 시작하는 메소드
    @Pointcut("execution(* *..upload*(..))")
    public void uploadMeth() {}


    /*
     * Pointcut - 조합 (위의 포인트 컷을 조합)
     */

    // layer 패키지 내 @Service 적용 클래스
    @Pointcut("layerPack() && svcAnno()")
    public void layerPackSvcAnno() {}

    // layer 패키지 내 @Controller, @RestController 적용 클래스
    @Pointcut("layerPack() && (ctlAnno() || restCtlAnno())")
    public void layerPackAllCtlAnno() {}

    // layer 패키지 내 @Service 적용 클래스애, "read" 로 시작하는 메소드 제외
    @Pointcut("layerPack() && svcAnno()") // && !readMeth()
    public void layerPackSvcAnnoExReadMeth() {}

    // domain 패키지 내 "write" 메소드
    @Pointcut("domainPack() && svcAnno() && writeMeth()")
    public void domainPackSvcAnnoWriteMeth() {}

    // domain 패키지 내 "edit" 메소드
    @Pointcut("domainPack() && svcAnno() && editMeth()")
    public void domainPackSvcAnnoEditMeth() {}

    // domain 패키지 내 "inactivate" 메소드
    @Pointcut("domainPack() && svcAnno() && inactivateMeth()")
    public void domainPackSvcAnnoInactivateMeth() {}

    // domain 패키지 내 "initialize" 메소드
    @Pointcut("domainPack() && svcAnno() && removeMeth()")
    public void domainPackSvcAnnoRemoveMeth() {}

    // domain 패키지 내 "join" 메소드
    @Pointcut("domainPack() && svcAnno() && joinMeth()")
    public void domainPackSvcAnnoJoinMeth() {}

    // domain 패키지 내 "withdraw" 메소드
    @Pointcut("domainPack() && svcAnno() && withdrawMeth()")
    public void domainPackSvcAnnoWithdrawMeth() {}

    // domain 패키지 내 "pay" 메소드
    @Pointcut("domainPack() && svcAnno() && payMeth()")
    public void domainPackSvcAnnoPayMeth() {}

    // domain 패키지 내 "refund" 메소드
    @Pointcut("domainPack() && svcAnno() && refundMeth()")
    public void domainPackSvcAnnoRefundMeth() {}

    // domain 패키지 내 "upload" 메소드
    @Pointcut("domainPack() && svcAnno() && uploadMeth()")
    public void domainPackSvcAnnoUploadMeth() {}

}
