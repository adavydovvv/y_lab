package ru.ylab.audit;

public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void logAction(AuditLog auditLog) {
        auditRepository.logAction(auditLog);
    }
}