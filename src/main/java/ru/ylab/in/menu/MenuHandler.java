package ru.ylab.in.menu;
import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.User;
import ru.ylab.security.SecurityManager;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuHandler {
    public static void display(Scanner scanner, UserController userController, CarController carController, OrderController orderController, AuditController auditController, User user) throws InterruptedException, SQLException {
        while (true) {
            System.out.println("=========== MAIN MENU ===========");

            if (SecurityManager.hasAccessToCarAndOrderMenu(AppConfig.getInstance().getAuthorizedUser())) {
                System.out.println("""
                Enter Menu:
                1. Car Menu
                2. Order Menu
                """);

                int action = scanner.nextInt();
                scanner.nextLine();

                switch (action) {
                    case 1:
                        CarMenu.display(scanner, carController, auditController, userController, orderController);
                        break;
                    case 2:
                        OrderMenu.display(scanner, userController, auditController, carController, orderController);
                        break;
                    default: System.out.println("Invalid action. Please try again.");
                }

            }
            if (SecurityManager.hasAccessToUserMenu(AppConfig.getInstance().getAuthorizedUser())) {
                UserMenu.display(scanner, userController, auditController, carController, orderController);
            }
            if (SecurityManager.hasAccessToClientMenu(AppConfig.getInstance().getAuthorizedUser())){
                ClientMenu.display(scanner, auditController, carController, orderController, userController);
            }
        }
    }

}
