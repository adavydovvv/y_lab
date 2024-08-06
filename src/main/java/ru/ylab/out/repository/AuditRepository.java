package ru.ylab.out.repository;

import ru.ylab.model.AuditLog;
import ru.ylab.out.repository.AuditRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuditRepository {
    private List<AuditLog> auditLogs = new ArrayList<>();


    public void logAction(AuditLog auditLog) {
        auditLogs.add(auditLog);
        appendLogToFile("audit_logs.txt", auditLog);
    }


    public List<AuditLog> getAllLogs() {
        return new ArrayList<>(auditLogs);
    }


    public List<AuditLog> filterLogsByUser(String username) {
        return auditLogs.stream()
                .filter(log -> log.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }



    public List<AuditLog> filterLogsByAction(String action) {
        return auditLogs.stream()
                .filter(log -> log.getAction().equals(action))
                .collect(Collectors.toList());
    }


    public List<AuditLog> filterLogsByDate(LocalDateTime from, LocalDateTime to) {
        return auditLogs.stream()
                .filter(log -> log.getTimestamp().isAfter(from) && log.getTimestamp().isBefore(to))
                .collect(Collectors.toList());
    }


    public void exportLogsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (AuditLog log : auditLogs) {
                writer.write(log.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void appendLogToFile(String filename, AuditLog auditLog) {
        String filePath = "audit_logs.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(auditLog.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
