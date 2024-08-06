package ru.ylab.in.menu;

import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.AuditLog;
import ru.ylab.model.Role;

import java.time.LocalDateTime;
import java.util.Scanner;

public class UserMenu {
    public static void display(Scanner scanner, UserController userController, AuditController auditController, CarController carController, OrderController orderController) throws InterruptedException {

        System.out.println("""
                ----- USER MENU -----
                1. Delete a user
                2. Change user role
                3. Change user password
                4. Get all users
                5. Get users sorted by First name
                6. Get users sorted by Last name
                7. Get users sorted by Email
                8. Get users sorted by Purchases count
                9. Get users filtered by First name
                10. Get users filtered by Last name
                11. Get users filtered by Email
                12. Get users filtered by Purchases count
                13. Logout
                14. Exit
                """);

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> deleteUser(scanner, userController, auditController);
            case 2 -> changeRole(scanner, userController, auditController);
            case 3 -> changeUserPassword(scanner, userController, auditController);
            case 4 -> getAllUsers(scanner, userController, auditController);
            case 5 -> getUsersSortedByFirstName(scanner, userController, auditController);
            case 6 -> getUsersSortedByLastName(scanner, userController, auditController);
            case 7 -> getUsersSortedByEmail(scanner, userController, auditController);
            case 8 -> getUsersSortedByPurchaseCount(scanner, userController, auditController);
            case 9 -> getUsersFilteredByFirstName(scanner, userController, auditController);
            case 10 -> getUsersFilteredByLastName(scanner, userController, auditController);
            case 11 -> getUsersFilteredByEmail(scanner, userController, auditController);
            case 12 -> getUsersFilteredByPurchasesCount(scanner, userController, auditController);
            case 13 -> AppConfig.logout(scanner, userController, carController, orderController, auditController);
            case 14-> AppConfig.exit(scanner);
            default -> System.out.println("Invalid action. Please try again.");
        }
    }

    private static void deleteUser(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter user username:");
        String username_fordelete = scanner.nextLine();
        userController.deleteUser(userController.getUserByUsername(username_fordelete));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Deleted user: " + username_fordelete));
        Thread.sleep(2000);
    }

    private static void changeRole(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Select role");
        System.out.println("1. CLIENT");
        System.out.println("2. MANAGER");
        int action5 = scanner.nextInt();
        switch (action5) {
            case 1:
                userController.updateUserRole(userController.getUserByUsername(username), Role.CLIENT);
                auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Changed user role to CLIENT for username: " + username));
                Thread.sleep(3000);
                break;
            case 2:
                userController.updateUserRole(userController.getUserByUsername(username), Role.MANAGER);
                auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Changed user role to MANAGER for username: " + username));
                Thread.sleep(3000);
                break;
            default:
                System.out.println("Invalid action. Please try again.");
        }
    }

    private static void changeUserPassword(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter new password:");
        String password = scanner.nextLine();
        userController.updateUserPassword(AppConfig.getInstance().getAuthorizedUser(), password);
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Changed password for username: " + AppConfig.getInstance().getAuthorizedUser().getUsername()));
        Thread.sleep(3000);
    }

    private static void getAllUsers(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println(userController.getUsers());
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Viewed all users"));
        Thread.sleep(3000);
    }

    private static void getUsersSortedByFirstName(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println(userController.getUsersSortedByFirstName());
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Sorted users by first name"));
        Thread.sleep(3000);
    }

    private static void getUsersSortedByLastName(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println(userController.getUsersSortedByLastName());
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Sorted users by last name"));
        Thread.sleep(3000);
    }

    private static void getUsersSortedByEmail(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println(userController.getUsersSortedByEmail());
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Sorted users by email"));
        Thread.sleep(3000);
    }

    private static void getUsersSortedByPurchaseCount(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println(userController.getUsersSortedByPurchasesCount());
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Sorted users by purchases count"));
        Thread.sleep(3000);
    }

    private static void getUsersFilteredByFirstName(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println(userController.filterUsersByFirstName(firstName));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered users by first name: " + firstName));
        Thread.sleep(3000);
    }

    private static void getUsersFilteredByLastName(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println(userController.filterUsersByLastName(lastName));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered users by last name: " + lastName));
        Thread.sleep(3000);
    }

    private static void getUsersFilteredByEmail(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println(userController.filterUsersByEmail(email));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered users by email: " + email));
        Thread.sleep(3000);
    }

    private static void getUsersFilteredByPurchasesCount(Scanner scanner, UserController userController, AuditController auditController) throws InterruptedException{
        System.out.println("Enter purchases count");
        int purchasesCount = scanner.nextInt();
        System.out.println(userController.filterUsersByPurchasesCount(purchasesCount));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered users by purchases count: " + purchasesCount));
        Thread.sleep(3000);
    }

}
