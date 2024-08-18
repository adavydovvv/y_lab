package ru.ylab.model;

import java.time.LocalDateTime;

public class AuditLog {

    private LocalDateTime timestamp;
    private User user;
    private String action;

    public AuditLog(LocalDateTime timestamp, User user, String action) {
        this.timestamp = timestamp;
        this.user = user;
        this.action = action;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUser(User user) {
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
