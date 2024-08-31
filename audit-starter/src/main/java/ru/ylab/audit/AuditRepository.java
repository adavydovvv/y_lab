package ru.ylab.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

@Repository
public class AuditRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String LOG_ACTION_SQL = "INSERT INTO carshop.log (id, timestamp, userId, action) " +
            "VALUES (nextval('public.log_id_seq'), ?, ?, ?)";

    @Autowired
    public AuditRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void logAction(AuditLog auditLog) {
        jdbcTemplate.update(LOG_ACTION_SQL, Timestamp.valueOf(auditLog.getTimestamp()),
                auditLog.getUser(), auditLog.getAction());

    }

    private void appendLogToFile(String filename, AuditLog auditLog) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(auditLog.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
