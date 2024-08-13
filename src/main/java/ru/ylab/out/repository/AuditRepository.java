package ru.ylab.out.repository;

import ru.ylab.model.AuditLog;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class AuditRepository {

    public void logAction(AuditLog auditLog) {
        String sql = "INSERT INTO carshop.log (id, timestamp, userId, action) " +
                "VALUES (nextval('public.log_id_seq'), ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(auditLog.getTimestamp()));
            stmt.setInt(2, auditLog.getUser().getUserId());
            stmt.setString(3, auditLog.getAction());


            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when adding a log: " + e.getMessage());
        }
        appendLogToFile("audit_logs.txt", auditLog);
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
