package ru.ylab.out.data;

import ru.ylab.model.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase {
    private static List<User> users = new ArrayList<>();
    private static List<Car> cars = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static List<AuditLog> auditLogs = new ArrayList<>();
    private static InMemoryDatabase instance;

    private InMemoryDatabase() {
        User user1 = new User(1, "admin", "admin", "A", "B", Role.ADMIN, "0", "a@dmin");
        User user2 = new User(2, "manager", "manager", "B", "A", Role.MANAGER, "0", "a@dmin");
        users.add(user1);
        users.add(user2);


        Car car = new Car(1,"Nissan","Teana", 2006, 900000, "Beige", "Very Good", 2, 180, 2.4, "Petrol");
        Car car2 = new Car(2,"BMW","M8", 2024, 9999999, "Beige", "Good", 2, 180, 2.4, "Diesel");
        cars.add(car);
        cars.add(car2);
    }

    public static InMemoryDatabase getInstance() {
        if (instance == null) {
            instance = new InMemoryDatabase();
        }
        return instance;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Car> getCars() {
        return cars;
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static List<AuditLog> getAuditLogs() {
        return auditLogs;
    }

    public static void clearDatabase() {
        users.clear();
        cars.clear();
        orders.clear();
        auditLogs.clear();
    }
}
