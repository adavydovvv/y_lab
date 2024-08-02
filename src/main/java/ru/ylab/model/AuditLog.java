package ru.ylab.model;

import java.time.LocalDateTime;

public class AuditLog {
    private LocalDateTime timestamp;
    private User user;
    private AuditAction action;

    public AuditLog(LocalDateTime timestamp, User user, AuditAction action) {
        this.timestamp = timestamp;
        this.user = user;
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }
}
