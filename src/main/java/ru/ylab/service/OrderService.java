package ru.ylab.service;

import ru.ylab.config.AppConfig;
import ru.ylab.model.*;
import ru.ylab.out.repository.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private AppConfig appConfig = AppConfig.getInstance();
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            orderRepository.addOrder(order);
            System.out.println("Order added");
        }
        else {
            System.out.println("You don't have permission to add a order");
        }
    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            orderRepository.changeOrderStatus(order, status);
            System.out.println("Order status changed");
        }
        else {
            System.out.println("You don't have permission to change order status");
        }
    }

    public List<String> getOrders() {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            List<String> orderNames = new ArrayList<>();
            for (Order order : orderRepository.getOrders()) {
                orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus());
            }
            return orderNames;
        }
        else {
            return null;
        }
    }

    public List<Order> getOrdersforTests() {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            return orderRepository.getOrders();
        }
        else {
            return null;
        }
    }

    public void deleteOrder(Order order) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            orderRepository.deleteOrder(order);
        }
        else{
            System.out.println("You don't have permission to delete a order");
        }
    }

    public Order getOrderById(int id) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            return orderRepository.getOrderById(id);
        }
        else {
            return null;
        }
    }

    public List<String> getOrdersByStatus(OrderStatus status) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            List<String> orderNames = new ArrayList<>();
            for (Order order : orderRepository.filterOrdersByStatus(status)) {
                orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus() + " Date: " + order.getDate());
            }
            return orderNames;
        }
        else {
            return null;
        }
    }

    public List<String> getOrdersByCar(Car car) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            List<String> orderNames = new ArrayList<>();
            for (Order order : orderRepository.filterOrdersByCar(car)) {
                orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus() + " Date: " + order.getDate());
            }
            return orderNames;
        }
        else {
            return null;
        }
    }

    public List<String> filterOrdersByClient(User user) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            List<String> orderNames = new ArrayList<>();
            for (Order order : orderRepository.filterOrdersByClient(user)) {
                orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus()+ " Date: " + order.getDate());
            }
            return orderNames;
        }
        else {
            return null;
        }
    }

    public List<String> filterOrdersByDate(LocalDate date) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            List<String> orderNames = new ArrayList<>();
            for (Order order : orderRepository.filterOrdersByDate(date)) {
                orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus() + " Date: " + order.getDate());
            }
            return orderNames;
        }
        else {
            return null;
        }
    }

    public int getLastOrderId() {
        return orderRepository.getLastOrderId();
    }
}
