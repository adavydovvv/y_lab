package ru.ylab.config;

import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.in.menu.MainMenu;
import ru.ylab.model.User;

import java.sql.SQLException;
import java.util.Scanner;

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

    public static void logout(Scanner scanner, UserController userController, CarController carController, OrderController orderController, AuditController auditController) throws InterruptedException, SQLException {
        AppConfig.getInstance().setAuthorizedUser(null);
        System.out.println("Logged out successfully.");
        MainMenu.display(scanner, userController, carController, orderController, auditController);
    }
    public static void exit(Scanner scanner){
        System.out.println("Exiting...");
        scanner.close();
        System.exit(0);
    }
}
