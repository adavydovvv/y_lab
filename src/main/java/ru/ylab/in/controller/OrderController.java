package ru.ylab.in.controller;

import ru.ylab.model.Car;
import ru.ylab.model.Order;
import ru.ylab.model.OrderStatus;
import ru.ylab.model.User;
import ru.ylab.service.OrderService;

import java.time.LocalDate;
import java.util.List;

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void addOrder(Order order) {
        orderService.addOrder(order);
    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        orderService.changeOrderStatus(order, status);
    }

    public List<String> getOrders() {
        return orderService.getOrders();
    }

    public void deleteOrder(Order order) {
        orderService.deleteOrder(order);
    }

    public Order getOrderById(int id) {
        return orderService.getOrderById(id);
    }

    public List<String> getOrdersByStatus(OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }


    public List<String> getOrdersByCar(Car car) {
        return orderService.getOrdersByCar(car);
    }

    public List<String> filterOrdersByClient(User user) {
        return orderService.filterOrdersByClient(user);
    }

    public int getLastOrderId() {
        return orderService.getLastOrderId();
    }

    public List<String> filterOrdersByDate(LocalDate date) {
        return orderService.filterOrdersByDate(date);
    }
    public List<Order> getOrdersforTests() {
        return orderService.getOrdersforTests();
    }
}
