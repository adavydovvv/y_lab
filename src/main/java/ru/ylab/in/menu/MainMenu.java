package ru.ylab.in.menu;

import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.User;

import java.util.Scanner;

public class MainMenu {
    public static void display(Scanner scanner, UserController userController, CarController carController, OrderController orderController, AuditController auditController) throws InterruptedException {
        System.out.println("=========== WELCOME TO CAR SHOP ===========");
        System.out.println("--- log in to your account ---");

        while (true) {
            System.out.println("""
                    # select an action #
                    1. Sign in
                    2. Sign up
                    3. Exit
                    """);

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1 -> signIn(scanner, userController, carController, orderController, auditController);
                case 2 -> signUp(scanner, userController, carController, orderController, auditController);
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private static void signIn(Scanner scanner, UserController userController, CarController carController, OrderController orderController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        userController.loginUser(username, password);
        User user = AppConfig.getInstance().getAuthorizedUser();
        if (user != null) {
            MenuHandler.display(scanner, userController, carController, orderController, auditController, user);
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static void signUp(Scanner scanner, UserController userController, CarController carController, OrderController orderController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter username: ");
        String newUsername = scanner.nextLine();
        System.out.println("Enter password: ");
        String newPassword = scanner.nextLine();
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        User user = new User(userController.getLastUserId() + 1, newUsername, newPassword, firstName, lastName, phoneNumber, email);
        userController.registerUser(user);

        userController.loginUser(newUsername, newPassword);

        User loggedInUser = AppConfig.getInstance().getAuthorizedUser();
        if (loggedInUser != null) {
            MenuHandler.display(scanner, userController, carController, orderController, auditController, loggedInUser);
        } else {
            System.out.println("Registration failed. Try again.");
        }
    }
}
