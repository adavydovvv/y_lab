package ru.ylab.config;

import ru.ylab.model.User;

public class AppConfig {
    private static AppConfig instance;
    private User authorizedUser;

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(User authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

}
