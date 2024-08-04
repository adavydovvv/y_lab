package ru.ylab.in.app;

import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.*;
import ru.ylab.out.repository.AuditRepository;
import ru.ylab.out.repository.CarRepository;
import ru.ylab.out.repository.OrderRepository;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.AuditService;
import ru.ylab.service.CarService;
import ru.ylab.service.OrderService;
import ru.ylab.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class CarShop {
    public static void main(String[] args) throws InterruptedException {
        AppConfig appConfig = AppConfig.getInstance();

        UserRepository userRepository = new UserRepository();
        CarRepository carRepository = new CarRepository();
        OrderRepository orderRepository = new OrderRepository();
        AuditRepository auditRepository = new AuditRepository();

        UserService userService = new UserService(userRepository);
        CarService carService = new CarService(carRepository);
        OrderService orderService = new OrderService(orderRepository);
        AuditService auditService = new AuditService(auditRepository);

        UserController userController = new UserController(userService);
        CarController carController = new CarController(carService);
        OrderController orderController = new OrderController(orderService);
        AuditController auditController = new AuditController(auditService);

        Scanner scanner = new Scanner(System.in);

        login(scanner, userController, carController, orderController, auditController, appConfig);


    }


    private static void login(Scanner scanner, UserController userController, CarController carController, OrderController orderController, AuditController auditController, AppConfig appConfig) throws InterruptedException {
        System.out.println("=========== WELCOME TO CAR SHOP ===========");
        System.out.println("--- log in to your account ---");
        System.out.println("# select an action #");
        System.out.println("1. Sign in");
        System.out.println("2. Sign up");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                System.out.println("Enter username: ");
                String username = scanner.nextLine();
                System.out.println("Enter password: ");
                String password = scanner.nextLine();
                userController.loginUser(username, password);
                if (appConfig.getAuthorizedUser() != null) {
                    auditController.logAction(new AuditLog(LocalDateTime.now(), appConfig.getAuthorizedUser(), "User logged in"));
                    handleUserActions(scanner, userController, carController, orderController, auditController);
                } else {
                    auditController.logAction(new AuditLog(LocalDateTime.now(), appConfig.getAuthorizedUser(), "Failed login attempt"));
                    login(scanner, userController, carController, orderController, auditController, AppConfig.getInstance());
                }
                break;
            case 2:
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

                if (appConfig.getAuthorizedUser() != null) {
                    auditController.logAction(new AuditLog(LocalDateTime.now(), appConfig.getAuthorizedUser(), "User registered and logged in"));
                    handleUserActions(scanner, userController, carController, orderController, auditController);
                } else {
                    auditController.logAction(new AuditLog(LocalDateTime.now(), appConfig.getAuthorizedUser(), "Failed registration attempt"));
                    login(scanner, userController, carController, orderController, auditController, AppConfig.getInstance());
                }
                break;
            default:
                System.out.println("Invalid action. Please try again.");
        }
    }

    private static void handleUserActions(Scanner scanner, UserController userController, CarController carController, OrderController orderController, AuditController auditController) throws InterruptedException {
        while (true) {
            System.out.println("=========== MAIN MENU ===========");
            System.out.println("# select an action #");

            System.out.println("");

            System.out.println("----- CAR MENU -----");

            System.out.println("1. View all cars");
            System.out.println("2. Add a new car");
            System.out.println("3. Edit a car price");
            System.out.println("4. Delete a car");
            System.out.println("5. Filter cars by Brand");
            System.out.println("6. Filter cars by Model");
            System.out.println("7. Filter cars by Price");
            System.out.println("8. Filter cars by Condition");
            System.out.println("9. Filter cars by Engine Type");

            System.out.println("");

            System.out.println("----- ORDER MENU -----");

            System.out.println("10. Create a new order");
            System.out.println("11. Change order status");
            System.out.println("12. View all orders");
            System.out.println("13. Delete a order");
            System.out.println("14. Get orders by status");
            System.out.println("15. Get orders by car");
            System.out.println("16. Get orders by username");
            System.out.println("17. Get orders by date");

            System.out.println("");

            System.out.println("----- USER MENU -----");
            System.out.println("18. Delete a user");
            System.out.println("19. Change user role");
            System.out.println("20. Change user password");
            System.out.println("21. Get all users");
            System.out.println("22. Get users sorted by First name");
            System.out.println("23. Get users sorted by Last name");
            System.out.println("24. Get users sorted by Email");
            System.out.println("25. Get users sorted by Purchases count");
            System.out.println("26. Get users filtered by First name");
            System.out.println("27. Get users filtered by Last name");
            System.out.println("28. Get users filtered by Email");
            System.out.println("29. Get users filtered by Purchases count");

            System.out.println("");

            System.out.println("30. Logout");
            System.out.println("31. Exit");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    System.out.println(carController.getCars());
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Viewed all cars"));
                    Thread.sleep(5000);
                    break;
                case 2:
                    try {
                        System.out.println("Enter car brand: ");
                        String brand = scanner.nextLine();
                        System.out.println("Enter car model: ");
                        String model = scanner.nextLine();
                        System.out.println("Enter car year: ");
                        int year = scanner.nextInt();
                        System.out.println("Enter car price: ");
                        int price = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter car color: ");
                        String color = scanner.nextLine();
                        System.out.println("Enter car condition: ");
                        String condition = scanner.nextLine();
                        System.out.println("Enter number of owners: ");
                        int owners = scanner.nextInt();
                        System.out.println("Enter car horsepower: ");
                        int horsepower = scanner.nextInt();
                        System.out.println("Enter car engine capacity: ");
                        double engineCapacity = scanner.nextDouble();
                        System.out.println("Enter car engine type: ");
                        scanner.nextLine(); // Correct the issue with scanner skipping
                        String engineType = scanner.nextLine();

                        Car car = new Car(carController.getLastCarId() + 1, brand, model, year, price, color, condition, owners, horsepower, engineCapacity, engineType);
                        carController.addCar(car);
                        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Added a new car: " + car.toString()));
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        System.out.println("Сheck the entered data");
                    }
                    break;
                case 3:
                    System.out.println("Enter car id to edit: ");
                    int carIdToEdit = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter car price: ");
                    int priceToEdit = scanner.nextInt();

                    carController.updateCarPrice(carController.getCarById(carIdToEdit), priceToEdit);
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Edited car price for car ID: " + carIdToEdit));
                    Thread.sleep(5000);
                    break;
                case 4:
                    System.out.println("Enter car id to delete: ");
                    int carIdToDelete = scanner.nextInt();
                    scanner.nextLine();
                    carController.removeCar(carController.getCarById(carIdToDelete));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Deleted car with ID: " + carIdToDelete));
                    Thread.sleep(3000);
                    break;

                case 5:
                    System.out.println("Enter the brand:");
                    String brand = scanner.nextLine();
                    System.out.println(carController.filterCarsByBrand(brand));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by brand: " + brand));
                    Thread.sleep(5000);
                    break;
                case 6:
                    System.out.println("Enter the model:");
                    String model = scanner.nextLine();
                    System.out.println(carController.filterCarsByModel(model));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by model: " + model));
                    Thread.sleep(5000);
                    break;
                case 7:
                    System.out.println("Enter the price:");
                    int price = scanner.nextInt();
                    System.out.println(carController.filterCarsByPrice(price));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by price: " + price));
                    Thread.sleep(5000);
                    break;
                case 8:
                    System.out.println("Enter the condition:");
                    String condition = scanner.nextLine();
                    System.out.println(carController.filterCarsByCondition(condition));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by condition: " + condition));
                    Thread.sleep(5000);
                    break;
                case 9:
                    System.out.println("Enter the engine type:");
                    String engineType = scanner.nextLine();
                    System.out.println(carController.filterCarsByEngineType(engineType));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by engine type: " + engineType));
                    Thread.sleep(5000);
                    break;
                case 10:
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
                                Order order = new Order(orderController.getLastOrderId() + 1, AppConfig.getInstance().getAuthorizedUser(), carController.getCarById(carIdToOrder), OrderStatus.PENDING, priceToOrder, descriptionToOrder, LocalDate.now());
                                carController.getCarById(carIdToOrder).setCar_available(false);
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
                                Order order2 = new Order(orderController.getLastOrderId() + 1, AppConfig.getInstance().getAuthorizedUser(), carController.getCarById(carId), OrderStatus.PENDING, LocalDate.now());
                                AppConfig.getInstance().getAuthorizedUser().setNumber_of_purchases(AppConfig.getInstance().getAuthorizedUser().getNumber_of_purchases() + 1);
                                carController.getCarById(carId).setCar_available(false);
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
                    break;
                case 11:
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
                    break;
                case 12:
                    System.out.println(orderController.getOrders());
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Viewed all orders"));
                    Thread.sleep(3000);
                    break;
                case 13:
                    System.out.println("Enter order id to delete:");
                    int orderIdToDelete = scanner.nextInt();
                    scanner.nextLine();
                    orderController.getOrderById(orderIdToDelete).getCar().setCar_available(true);
                    orderController.deleteOrder(orderController.getOrderById(orderIdToDelete));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Deleted order with ID: " + orderIdToDelete));
                    Thread.sleep(3000);
                    break;
                case 14:
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
                    break;
                case 15:
                    System.out.println("Enter car id:");
                    int carId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(orderController.getOrdersByCar(carController.getCarById(carId)));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by car ID: " + carId));
                    Thread.sleep(3000);
                    break;
                case 16:
                    System.out.println("Enter username:");
                    String client = scanner.nextLine();
                    System.out.println(orderController.filterOrdersByClient(userController.getUserByUsername(client)));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by client username: " + client));
                    Thread.sleep(3000);
                    break;
                case 17:
                    System.out.println("Enter date (YYYY-MM-DD)");
                    String date_input = scanner.nextLine();
                    LocalDate date = LocalDate.parse(date_input, DateTimeFormatter.ISO_LOCAL_DATE);
                    System.out.println(orderController.filterOrdersByDate(date));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered orders by date: " + date));
                    Thread.sleep(3000);
                    break;
                case 18:
                    System.out.println("Enter user username:");
                    String username_fordelete = scanner.nextLine();
                    userController.deleteUser(userController.getUserByUsername(username_fordelete));
                    Thread.sleep(2000);
                    break;
                case 19:
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
                    break;
                case 20:
                    System.out.println("Enter new password:");
                    String password = scanner.nextLine();
                    userController.updateUserPassword(AppConfig.getInstance().getAuthorizedUser(), password);
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Changed password for username: " + AppConfig.getInstance().getAuthorizedUser().getUsername()));
                    Thread.sleep(3000);
                    break;
                case 21:
                    System.out.println(userController.getUsers());
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Viewed all users"));
                    Thread.sleep(3000);
                    break;
                case 22:
                    System.out.println(userController.getUsersSortedByFirstName());
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Sorted users by first name"));
                    Thread.sleep(3000);
                    break;
                case 23:
                    System.out.println(userController.getUsersSortedByLastName());
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Sorted users by last name"));
                    Thread.sleep(3000);
                    break;
                case 24:
                    System.out.println(userController.getUsersSortedByEmail());
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Sorted users by email"));
                    Thread.sleep(3000);
                    break;
                case 25:
                    System.out.println(userController.getUsersSortedByPurchasesCount());
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Sorted users by purchases count"));
                    Thread.sleep(3000);
                    break;
                case 26:
                    System.out.println("Enter first name:");
                    String firstName = scanner.nextLine();
                    System.out.println(userController.filterUsersByFirstName(firstName));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered users by first name: " + firstName));
                    Thread.sleep(3000);
                    break;
                case 27:
                    System.out.println("Enter last name:");
                    String lastName = scanner.nextLine();
                    System.out.println(userController.filterUsersByLastName(lastName));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered users by last name: " + lastName));
                    Thread.sleep(3000);
                    break;
                case 28:
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println(userController.filterUsersByEmail(email));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered users by email: " + email));
                    Thread.sleep(3000);
                    break;
                case 29:
                    System.out.println("Enter purchases count");
                    int purchasesCount = scanner.nextInt();
                    System.out.println(userController.filterUsersByPurchasesCount(purchasesCount));
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered users by purchases count: " + purchasesCount));
                    Thread.sleep(3000);
                    break;
                case 30:
                    AppConfig.getInstance().setAuthorizedUser(null);
                    System.out.println("Logged out successfully.");
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Logged out"));
                    login(scanner, userController, carController, orderController, auditController, AppConfig.getInstance());
                    return;
                case 31:
                    System.out.println("Exiting...");
                    auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Exited the system"));
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid action. Please try again.");
            }
        }
    }


}

