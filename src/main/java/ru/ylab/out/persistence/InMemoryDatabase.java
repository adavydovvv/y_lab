package ru.ylab.out.persistence;

import ru.ylab.model.AuditLog;
import ru.ylab.model.Car;
import ru.ylab.model.Order;
import ru.ylab.model.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase {
    private static List<User> users = new ArrayList<>();
    private static List<Car> cars = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static List<AuditLog> auditLogs = new ArrayList<>();

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
