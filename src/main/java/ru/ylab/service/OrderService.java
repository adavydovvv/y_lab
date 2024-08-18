package ru.ylab.service;

import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.CarController;
import ru.ylab.model.*;
import ru.ylab.out.repository.CarRepository;
import ru.ylab.out.repository.OrderRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private AppConfig appConfig = AppConfig.getInstance();
    private final OrderRepository orderRepository;
    private final CarController carController;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    public void addOrder(Order order) throws SQLException {
        orderRepository.addOrder(order);
        System.out.println("Order added");

    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        orderRepository.changeOrderStatus(order, status);
        System.out.println("Order status changed");
    }

    public List<String> getOrders() {
        List<String> orderNames = new ArrayList<>();
        for (Order order : orderRepository.getOrders()) {
            orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus());
        }
        return orderNames;

    }

    public List<Order> getOrdersforTests() {
        return orderRepository.getOrders();
    }


    public void deleteOrder(Order order) {
        orderRepository.deleteOrder(order);
        System.out.println("Order deleted");
    }

    public Order getOrderById(int id) {
        return orderRepository.getOrderById(id);
    }

    public List<String> getOrdersByStatus(OrderStatus status) {
        List<String> orderNames = new ArrayList<>();
        for (Order order : orderRepository.filterOrdersByStatus(status)) {
            orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus() + " Date: " + order.getDate());
        }
        return orderNames;

    }

    public List<String> getOrdersByCar(Car car) {
        List<String> orderNames = new ArrayList<>();
        for (Order order : orderRepository.filterOrdersByCar(car)) {
            orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus() + " Date: " + order.getDate());
        }
        return orderNames;

    }

    public List<String> filterOrdersByClient(User user) {
        List<String> orderNames = new ArrayList<>();
        for (Order order : orderRepository.filterOrdersByClient(user)) {
            orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus()+ " Date: " + order.getDate());
        }
        return orderNames;

    }

    public List<String> filterOrdersByDate(LocalDate date) {
        List<String> orderNames = new ArrayList<>();
        for (Order order : orderRepository.filterOrdersByDate(date)) {
            orderNames.add("ID: " + order.getOrderId() + " " + order.getCar().getBrand() + " " + order.getCar().getModel() + " Status: " + order.getStatus() + " Date: " + order.getDate());
        }
        return orderNames;
    }
    public int getLastOrderId() {
        return orderRepository.getLastOrderId();
    }
}
