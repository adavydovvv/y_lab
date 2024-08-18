package ru.ylab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.AuditLog;
import ru.ylab.out.repository.AuditRepository;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.AuditService;
import ru.ylab.service.UserService;

import java.time.LocalDateTime;

@Aspect
public class AuditAspect {
    private final AuditController auditController = new AuditController(new AuditService(new AuditRepository()));
    private final UserController userController = new UserController(new UserService(new UserRepository()));

    @After("execution(* ru.ylab.in.controller.*.*(..))")
    public void logUserAction(JoinPoint joinPoint) {

        String action = joinPoint.getSignature().getName();

        AuditLog auditLog = new AuditLog(LocalDateTime.now(), userController.getUserByUsername("admin"), action);

        System.out.println(auditLog);
        auditController.logAction(auditLog);
    }
}
