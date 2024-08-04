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

    public List<AuditLog> getAllLogs() {
        return auditService.getAllLogs();
    }

    public List<AuditLog> filterLogsByUser(String username) {
        return auditService.filterLogsByUser(username);
    }

    public List<AuditLog> filterLogsByAction(String action) {
        return auditService.filterLogsByAction(action);
    }

    public List<AuditLog> filterLogsByDate(LocalDateTime from, LocalDateTime to) {
        return auditService.filterLogsByDate(from, to);
    }

    public void exportLogsToFile(String filename) {
        auditService.exportLogsToFile(filename);
    }
}