package ru.ylab.out.repository;

import ru.ylab.model.*;
import ru.ylab.out.data.InMemoryDatabase;

import java.time.LocalDate;
import java.util.List;

public class OrderRepository {

    private List<Order> orders = InMemoryDatabase.getInstance().getOrders();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);
    }

    public void deleteOrder(Order order) {
        orders.remove(order);
    }

    public Order getOrderById(int id) {
        return orders.stream()
                .filter(order -> order.getOrderId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Order> filterOrdersByCar(Car car) {
        return orders.stream()
                .filter(order -> order.getCar().equals(car))
                .toList();
    }

    public List<Order> filterOrdersByStatus(OrderStatus status) {
        return orders.stream()
                .filter(order -> order.getStatus().equals(status))
                .toList();
    }

    public List<Order> filterOrdersByClient(User user) {
        return orders.stream()
                .filter(order -> order.getCustomer().equals(user))
                .toList();
    }


    public List<Order> filterOrdersByDate(LocalDate date) {
        return orders.stream()
                .filter(order -> order.getDate().equals(date))
                .toList();
    }

    public int getLastOrderId() {
        return orders.size();
    }
}
