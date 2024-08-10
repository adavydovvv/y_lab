package ru.ylab.in.menu;

import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.AuditLog;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ClientMenu {

    public static void display(Scanner scanner, AuditController auditController, CarController carController, OrderController orderController, UserController userController) throws InterruptedException, SQLException {

        System.out.println("""
                ----- CLIENT MENU -----
                1. View all cars
                2. Filter cars by Brand
                3. Filter cars by Model
                4. Filter cars by Price
                5. Filter cars by Condition
                6. Filter cars by Engine Type
                7. Create a new order
                8. Change password
                9. Logout
                10. Exit
                """);

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> CarMenu.getCars(carController, auditController);
            case 2 -> CarMenu.filterCarsByBrand(scanner, carController, auditController);
            case 3 -> CarMenu.filterCarsByModel(scanner, carController, auditController);
            case 4 -> CarMenu.filterCarsByPrice(scanner, carController, auditController);
            case 5 -> CarMenu.filterCarsByCondition(scanner, carController, auditController);
            case 6 -> CarMenu.filterCarsByEngineType(scanner, carController, auditController);
            case 7 -> OrderMenu.createOrder(scanner, carController, orderController, auditController, userController);
            case 8 -> UserMenu.changeUserPassword(scanner, userController, auditController);
            case 9 -> AppConfig.logout(scanner, userController, carController, orderController, auditController);
            case 10 -> AppConfig.exit(scanner);
            default -> System.out.println("Invalid action. Please try again.");
        }

    }


}
