package ru.ylab.in.menu;

import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.AuditLog;
import ru.ylab.model.Order;
import ru.ylab.model.OrderStatus;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class OrderMenu {
    public static void display(Scanner scanner, UserController userController, AuditController auditController, CarController carController, OrderController orderController) throws InterruptedException, SQLException {

        System.out.println("""
                ----- ORDER MENU -----
                1. Create a new order
                2. Change order status
                3. View all orders
                4. Delete a order
                5. Get orders by status
                6. Get orders by car
                7. Get orders by username
                8. Get orders by date
                9. Logout
                10. Exit
                """);

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> createOrder(scanner, carController, orderController, auditController, userController);
            case 2 -> changeOrderStatus(scanner, orderController, auditController);
            case 3 -> viewAllOrders(scanner, orderController, auditController);
            case 4 -> deleteOrder(scanner, orderController, auditController);
            case 5 -> getOrdersByStatus(scanner, orderController, auditController);
            case 6 -> getOrdersByCar(scanner, orderController, carController, auditController);
            case 7 -> getOrdersByUsername(scanner, orderController, userController, auditController);
            case 8 -> getOrdersByDate(scanner, orderController, auditController);
            case 9 -> AppConfig.logout(scanner, userController, carController, orderController, auditController);
            case 10 -> AppConfig.exit(scanner);
            default -> System.out.println("Invalid action. Please try again.");
        }
    }



    public static void createOrder(Scanner scanner, CarController carController, OrderController orderController, AuditController auditController, UserController userController) throws InterruptedException, SQLException {
        System.out.println("1. Order a service");
        System.out.println("2. Order a car");
        int action2 = scanner.nextInt();
        switch (action2) {
            case 1:
                System.out.println("Enter car id: ");
                int carIdToOrder = scanner.nextInt();
                scanner.nextLine();
                if (carController.getCarById(carIdToOrder).isCar_available()) {
                    System.out.println("Enter price of service: ");
                    int priceToOrder = scanner.nextInt();
                    System.out.println("Enter the description of service: ");
                    scanner.nextLine();
                    String descriptionToOrder = scanner.nextLine();
                    Order order = new Order(orderController.getLastOrderId() + 1, AppConfig.getInstance().getAuthorizedUser(), carController.getCarById(carIdToOrder), OrderStatus.PENDING, priceToOrder, descriptionToOrder, Date.valueOf(LocalDate.now()));
                    carController.updateCarIsAvailable(carController.getCarById(carIdToOrder), false);
                    AppConfig.getInstance().getAuthorizedUser().setNumber_of_purchases(AppConfig.getInstance().getAuthorizedUser().getNumber_of_purchases() + 1);

                    orderController.addOrder(order);
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Ordered a service for car ID: " + carIdToOrder));
                    Thread.sleep(5000);
                } else {
                    System.out.println("This car is not available");
                }
                break;
            case 2:
                System.out.println("Enter car id: ");
                int carId = scanner.nextInt();
                scanner.nextLine();
                if (carController.getCarById(carId).isCar_available()) {
                    Order order2 = new Order(orderController.getLastOrderId() + 1, AppConfig.getInstance().getAuthorizedUser(), carController.getCarById(carId), OrderStatus.PENDING, Date.valueOf(LocalDate.now()));
                    AppConfig.getInstance().getAuthorizedUser().setNumber_of_purchases(AppConfig.getInstance().getAuthorizedUser().getNumber_of_purchases() + 1);
                    userController.updateUserPurchasesCount(AppConfig.getInstance().getAuthorizedUser());
                    carController.updateCarIsAvailable(carController.getCarById(carId), false);
                    orderController.addOrder(order2);
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Ordered a car with ID: " + carId));
                    Thread.sleep(5000);
                } else {
                    System.out.println("This car is not available");
                }
                break;
            default:
                System.out.println("Invalid action. Please try again.");
        }
    }
    private static void changeOrderStatus(Scanner scanner, OrderController orderController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter order id: ");
        int orderId = scanner.nextInt();
        System.out.println("Enter order status: ");

        System.out.println("1. PENDING");
        System.out.println("2. CANCELLED");
        System.out.println("3. CONFIRMED");

        int action3 = scanner.nextInt();
        switch (action3) {
            case 1:
                orderController.changeOrderStatus(orderController.getOrderById(orderId), OrderStatus.PENDING);
                auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Changed order status to PENDING for order ID: " + orderId));
                Thread.sleep(3000);
                break;
            case 2:
                orderController.changeOrderStatus(orderController.getOrderById(orderId), OrderStatus.CANCELLED);
                auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Changed order status to CANCELLED for order ID: " + orderId));
                Thread.sleep(3000);
                break;
            case 3:
                orderController.changeOrderStatus(orderController.getOrderById(orderId), OrderStatus.CONFIRMED);
                auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Changed order status to CONFIRMED for order ID: " + orderId));
                Thread.sleep(3000);
                break;
            default:
                System.out.println("Invalid action. Please try again.");
        }
    }

    private static void viewAllOrders(Scanner scanner, OrderController orderController, AuditController auditController) throws InterruptedException {
        System.out.println(orderController.getOrders());
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Viewed all orders"));
        Thread.sleep(3000);
    }

    private static void deleteOrder(Scanner scanner, OrderController orderController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter order id to delete:");
        int orderIdToDelete = scanner.nextInt();
        scanner.nextLine();
        orderController.getOrderById(orderIdToDelete).getCar().setCar_available(true);
        orderController.deleteOrder(orderController.getOrderById(orderIdToDelete));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Deleted order with ID: " + orderIdToDelete));
        Thread.sleep(3000);
    }

    private static void getOrdersByStatus(Scanner scanner, OrderController orderController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter order status:");
        System.out.println("1. PENDING");
        System.out.println("2. CANCELLED");
        System.out.println("3. CONFIRMED");
        int action4 = scanner.nextInt();
        switch (action4) {
            case 1:
                System.out.println(orderController.getOrdersByStatus(OrderStatus.PENDING));
                auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by status: PENDING"));
                Thread.sleep(3000);
                break;
            case 2:
                System.out.println(orderController.getOrdersByStatus(OrderStatus.CANCELLED));
                auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by status: CANCELLED"));
                Thread.sleep(3000);
                break;
            case 3:
                System.out.println(orderController.getOrdersByStatus(OrderStatus.CONFIRMED));
                auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by status: CONFIRMED"));
                Thread.sleep(3000);
                break;
            default:
                System.out.println("Invalid action. Please try again.");
        }
    }

    private static void getOrdersByCar(Scanner scanner, OrderController orderController, CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter car id:");
        int carId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(orderController.getOrdersByCar(carController.getCarById(carId)));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by car ID: " + carId));
        Thread.sleep(3000);
    }

    private static void getOrdersByUsername(Scanner scanner, OrderController orderController, UserController userController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter username:");
        String client = scanner.nextLine();
        System.out.println(orderController.filterOrdersByClient(userController.getUserByUsername(client)));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by client username: " + client));
        Thread.sleep(3000);
    }

    private static void getOrdersByDate(Scanner scanner, OrderController orderController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter date (YYYY-MM-DD)");
        String date_input = scanner.nextLine();
        LocalDate date = LocalDate.parse(date_input, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(orderController.filterOrdersByDate(date));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by date: " + date));
        Thread.sleep(3000);
    }

}
