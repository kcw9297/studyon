package studyon.app.infra.aop.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import studyon.app.infra.aop.LogInfo;
import studyon.app.common.enums.Action;
import studyon.app.common.enums.Entity;
import studyon.app.layer.domain.log.LogDTO;
import studyon.app.layer.domain.log.service.LogService;

import java.util.Map;
import java.util.Objects;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 서비스 메소드 처리 로그를 DB에 저장하기 위한 Aspect
 * @version 1.0
 * @author kcw97
 */
@Order(2)
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WriteServiceLogAspect {

    // TODO Purchase 쿠폰 사용, Coupon 메소드는 나중에 리스너로 처리
    private final LogService logService;

    // EntityType Value - EntityType Map
    private static final Map<String, Entity> ENTITY_TYPE_MAP = Map.ofEntries(
             Map.entry(Entity.MEMBER.getName(), Entity.MEMBER),
             Map.entry(Entity.TEACHER.getName(), Entity.TEACHER),
             Map.entry(Entity.LECTURE.getName(), Entity.LECTURE),
             Map.entry(Entity.LECTURE_QUESTION.getName(), Entity.LECTURE_QUESTION),
             Map.entry(Entity.LECTURE_ANSWER.getName(), Entity.LECTURE_ANSWER),
             Map.entry(Entity.PURCHASE.getName(), Entity.PURCHASE),
             Map.entry(Entity.REFUND.getName(), Entity.REFUND)
    );


    // 로직 성공 처리 로그 기록
    @AfterReturning(
            pointcut =
                    "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoWriteMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoEditMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoInactivateMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoRemoveMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoJoinMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoWithdrawMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoPayMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoRefundMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoCouponClsIssueMeth()",
            returning = "result"
    )
    public void afterSuccess(JoinPoint joinPoint, Object result) {
        generateSuccessLog(result, getActionType(joinPoint));
    }

    @AfterThrowing(
            pointcut =
                    "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoWriteMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoEditMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoInactivateMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoRemoveMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoJoinMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoWithdrawMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoPayMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoRefundMeth() || " +
                            "studyon.app.infra.aop.aspect.Pointcuts.domainPackSvcAnnoCouponClsIssueMeth()" +
                            "studyon.app.infra.aop.Pointcuts.domainPackSvcAnnoUploadMeth()",
            throwing = "e"
    )
    public void afterFail(JoinPoint joinPoint, Exception e) {
        generateFailLog(joinPoint, getActionType(joinPoint));
    }


    // 로직 실패 처리 로그 기록
    private Action getActionType(JoinPoint joinPoint) {

        // [1] 호출 메소드명 추출
        String methodName = joinPoint.getSignature().getName().toLowerCase();

        // [2] 호출 메소드에 따라 처리할 포인트컷
        if (methodName.startsWith("write")) return Action.WRITE;
        if (methodName.startsWith("edit")) return Action.EDIT;
        if (methodName.startsWith("inactivate")) return Action.INACTIVATE;
        if (methodName.startsWith("remove")) return Action.REMOVE;
        if (methodName.startsWith("join")) return Action.JOIN;
        if (methodName.startsWith("withdraw")) return Action.WITHDRAWAL;
        if (methodName.startsWith("pay")) return Action.PAY;
        if (methodName.startsWith("refund")) return Action.REFUND;
        if (methodName.startsWith("coupon")) return Action.COUPON_ISSUE;
        return Action.UNKNOWN;
    }


    // 성공 로그 작성
    private void generateSuccessLog(Object result, Action action) {

        // [1] 현재 HTTP Request 내 필요한 정보만 담은 Request Record 조회
        Request request = getRequestInfo();

        // [2] 반환 값 혹은 HTTP Request 둘 중 하나라도 null 이면 로그를 기록하지 않음
        if (Objects.nonNull(result) && Objects.nonNull(request)) {

            // [3] 로그에 필요한 정보 추출
            Pair<Long, Entity> target = extractTarget(result);

            // [4] 로그 생성 (target 정보가 잘 추출된 경우에만 생성)
            if (Objects.nonNull(target))
                logService.generate(toWriteDTO(request.loginMemberEmail, request.ipAddress, target.getFirst(), target.getSecond(), action, true));
        }
    }

    // 실패 로그 작성
    private void generateFailLog(JoinPoint joinPoint, Action action) {

        // [1] 현재 HTTP Request 내 필요한 정보만 담은 Request Record 조회
        Request request = getRequestInfo();

        // [2] 반환 값 혹은 HTTP Request 둘 중 하나라도 null 이면 로그를 기록하지 않음
        if (Objects.nonNull(request)) {

            // [3] 로그 생성
            logService.generate(toWriteDTO(request.loginMemberEmail, request.ipAddress, -1L, extractTargetEntity(joinPoint), action, false));
        }
    }

    // target 정보 추출
    private Pair<Long, Entity> extractTarget(Object result) {
        if (result instanceof LogInfo dto) return Pair.of(dto.getTargetId(), dto.getTargetEntity());
        return null;
    }

    // target entity 정보 추출
    private Entity extractTargetEntity(JoinPoint joinPoint) {

        // [1] 호출 클래스명 추출
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName().toLowerCase();

        // [2] 호출 클래스명 기반 영향을 받은 EntityType 추출 및 반환
        return ENTITY_TYPE_MAP.entrySet()
                .stream()
                .filter(entry -> className.startsWith(entry.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(Entity.UNKNOWN);
    }

    // HttpServletRequest 내 정보 추출
    private Request getRequestInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) return null;
        HttpServletRequest request = attributes.getRequest();

        String ipAddress = (String) request.getAttribute("ipAddress");
        String loginMemberEmail = (String) request.getAttribute("loginMemberEmail"); // 인터셉터에서 미리 세팅
        return new Request(ipAddress, loginMemberEmail);
    }

    // HttpServletRequest 정보를 저장할 임시 Record
    private record Request(String ipAddress, String loginMemberEmail) {}

    // 얻은 정보 기반 DTO 매핑
    private LogDTO.Generate toWriteDTO(
            String email, String ipAddress, Long entityId, Entity entity, Action action, Boolean isSuccess) {

        return LogDTO.Generate.builder()
                .email(email)
                .ipAddress(ipAddress)
                .entityId(entityId)
                .entity(entity)
                .action(action)
                .isSuccess(isSuccess)
                .build();
    }


}


