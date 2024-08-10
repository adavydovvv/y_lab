package ru.ylab.service;

import ru.ylab.model.AuditLog;
import ru.ylab.out.repository.AuditRepository;


public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void logAction(AuditLog auditLog) {
        auditRepository.logAction(auditLog);
    }

}