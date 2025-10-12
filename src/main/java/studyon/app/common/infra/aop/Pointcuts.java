package studyon.app.common.infra.aop;

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
public class Pointcuts {

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
    @Pointcut("@annotation(studyon.app.common.annotation.aspect.RedissonLock)")
    public void redissonLockAnno() {}

    /*
     * Pointcut - package 범위
     */

    // studyon.app 내 패키지 (기본 패키지 주소)
    @Pointcut("execution(* studyon.app..*(..))")
    public void basePack() {}

    // studyon.app.domain 내 패키지 (기본 패키지 주소)
    @Pointcut("execution(* studyon.app.domain..*(..))")
    public void domainPack() {}

    /*
     * Pointcut - 클래스 이름
     */

    // "Admin~" 가 앞에 오는 클래스
    @Pointcut("execution(* *..Admin*.*(..))")
    public void adminCls() {}

    // "~Coupon" 가 앞에 오는 클래스
    @Pointcut("execution(* *..Coupon*.*(..))")
    public void couponCls() {}

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

    // "remove" 으로 시작하는 메소드
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

    // "issue" 으로 시작하는 메소드
    @Pointcut("execution(* *..issue*(..))")
    public void issueMeth() {}


    /*
     * Pointcut - 조합 (위의 포인트 컷을 조합)
     */

    // 기본 패키지 내 @Service 적용 클래스
    @Pointcut("basePack() && svcAnno()")
    public void basePackSvcAnno() {}

    // 기본 패키지 내 @Controller, @RestController 적용 클래스
    @Pointcut("basePack() && (ctlAnno() || restCtlAnno())")
    public void basePackAllCtlAnno() {}

    // 기본 패키지 내 @Service 적용 클래스애, "read" 로 시작하는 메소드 제외
    @Pointcut("basePack() && svcAnno() && !readMeth()")
    public void basePackSvcAnnoExReadMeth() {}

    // domain 패키지 내 "write" 메소드
    @Pointcut("domainPack() && svcAnno() && writeMeth()")
    public void domainPackSvcAnnoWriteMeth() {}

    // domain 패키지 내 "edit" 메소드
    @Pointcut("domainPack() && svcAnno() && editMeth()")
    public void domainPackSvcAnnoEditMeth() {}

    // domain 패키지 내 "inactivate" 메소드
    @Pointcut("domainPack() && svcAnno() && inactivateMeth()")
    public void domainPackSvcAnnoInactivateMeth() {}

    // domain 패키지 내 "remove" 메소드
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

    // domain 패키지 내 "issue" 메소드
    @Pointcut("domainPack() && svcAnno() && couponCls() && issueMeth()")
    public void domainPackSvcAnnoCouponClsIssueMeth() {}

}
