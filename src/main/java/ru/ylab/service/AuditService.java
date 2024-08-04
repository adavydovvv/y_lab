package ru.ylab.service;

import ru.ylab.model.AuditLog;
import ru.ylab.out.repository.AuditRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void logAction(AuditLog auditLog) {
        auditRepository.logAction(auditLog);
    }

    public List<AuditLog> getAllLogs() {
        return auditRepository.getAllLogs();
    }

    public List<AuditLog> filterLogsByUser(String username) {
        return auditRepository.filterLogsByUser(username);
    }

    public List<AuditLog> filterLogsByAction(String action) {
        return auditRepository.filterLogsByAction(action);
    }

    public List<AuditLog> filterLogsByDate(LocalDateTime from, LocalDateTime to) {
        return auditRepository.filterLogsByDate(from, to);
    }

    public void exportLogsToFile(String filename) {
        auditRepository.exportLogsToFile(filename);
    }
}