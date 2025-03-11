package ru.ylab.audit;

//import ru.ylab.model.User;

import java.time.LocalDateTime;

public class AuditLog {

    private LocalDateTime timestamp;
    private String user;
    private String action;

    public AuditLog(LocalDateTime timestamp, String user, String action) {
        this.timestamp = timestamp;
        this.user = user;
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "timestamp=" + timestamp +
                ", user=" + user +
                ", action='" + action + '\'' +
                '}';
    }

}
