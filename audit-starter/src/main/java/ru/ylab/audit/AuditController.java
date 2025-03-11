package ru.ylab.audit;

public class AuditController {
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    public void logAction(AuditLog auditLog) {
        auditService.logAction(auditLog);
    }

}