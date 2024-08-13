package ru.ylab.in.menu;


import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.AuditLog;
import ru.ylab.model.Car;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CarMenu {
    public static void display(Scanner scanner, CarController carController, AuditController auditController, UserController userController, OrderController orderController) throws InterruptedException, SQLException {

        System.out.println("""
                ----- CAR MENU -----
                1. View all cars
                2. Add a new car
                3. Edit a car price
                4. Delete a car
                5. Filter cars by Brand
                6. Filter cars by Model
                7. Filter cars by Price
                8. Filter cars by Condition
                9. Filter cars by Engine Type
                10. Logout
                11. Exit
                """);

        int action = scanner.nextInt();
        scanner.nextLine();


        switch (action) {
            case 1 -> getCars(carController, auditController);
            case 2 -> addCar(scanner, carController, auditController);
            case 3 -> editCarPrice(scanner, carController, auditController);
            case 4 -> deleteCar(scanner, carController, auditController);
            case 5 -> filterCarsByBrand(scanner, carController, auditController);
            case 6 -> filterCarsByModel(scanner, carController, auditController);
            case 7 -> filterCarsByPrice(scanner, carController, auditController);
            case 8 -> filterCarsByCondition(scanner, carController, auditController);
            case 9 -> filterCarsByEngineType(scanner, carController, auditController);
            case 10 -> AppConfig.logout(scanner, userController, carController, orderController, auditController);
            case 11 -> AppConfig.exit(scanner);
            default -> System.out.println("Invalid action. Please try again.");
        }
    }

    public static void getCars(CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println(carController.getCars());
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Viewed all cars"));
        Thread.sleep(5000);
    }

    private static void addCar(Scanner scanner, CarController carController, AuditController auditController){
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
    }

    private static void editCarPrice(Scanner scanner, CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter car id to edit: ");
        int carIdToEdit = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter car price: ");
        int priceToEdit = scanner.nextInt();

        carController.updateCarPrice(carController.getCarById(carIdToEdit), priceToEdit);
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Edited car price for car ID: " + carIdToEdit));
        Thread.sleep(5000);
    }

    private static void deleteCar(Scanner scanner, CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter car id to delete: ");
        int carIdToDelete = scanner.nextInt();
        scanner.nextLine();
        carController.removeCar(carController.getCarById(carIdToDelete));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Deleted car with ID: " + carIdToDelete));
        Thread.sleep(3000);
    }
    public static void filterCarsByBrand(Scanner scanner, CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter the brand:");
        String brand = scanner.nextLine();
        System.out.println(carController.filterCarsByBrand(brand));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by brand: " + brand));
        Thread.sleep(5000);
    }

    public static void filterCarsByModel(Scanner scanner, CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter the model:");
        String model = scanner.nextLine();
        System.out.println(carController.filterCarsByModel(model));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by model: " + model));
        Thread.sleep(5000);
    }

    public static void filterCarsByPrice(Scanner scanner, CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter the price:");
        int price = scanner.nextInt();
        System.out.println(carController.filterCarsByPrice(price));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by price: " + price));
        Thread.sleep(5000);
    }

    public static void filterCarsByCondition(Scanner scanner, CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter the condition:");
        String condition = scanner.nextLine();
        System.out.println(carController.filterCarsByCondition(condition));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by condition: " + condition));
        Thread.sleep(5000);
    }

    public static void filterCarsByEngineType(Scanner scanner, CarController carController, AuditController auditController) throws InterruptedException {
        System.out.println("Enter the engine type:");
        String engineType = scanner.nextLine();
        System.out.println(carController.filterCarsByEngineType(engineType));
        auditController.logAction(new AuditLog(LocalDateTime.now(), AppConfig.getInstance().getAuthorizedUser(), "Filtered cars by engine type: " + engineType));
        Thread.sleep(5000);
    }
}
