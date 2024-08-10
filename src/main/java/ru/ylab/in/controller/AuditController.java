package ru.ylab.in.controller;

import ru.ylab.model.AuditLog;
import ru.ylab.service.AuditService;

import java.time.LocalDateTime;
import java.util.List;

public class AuditController {
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    public void logAction(AuditLog auditLog) {
        auditService.logAction(auditLog);
    }

}