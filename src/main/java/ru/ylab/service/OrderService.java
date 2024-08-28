package ru.ylab.service;

import org.springframework.stereotype.Service;
import ru.ylab.dto.OrderDTO;
import ru.ylab.in.controller.CarController;
import ru.ylab.model.*;
import ru.ylab.out.repository.OrderRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(OrderDTO orderDTO) {
        Order order = convertToOrder(orderDTO);
        orderRepository.addOrder(order);
    }

    private Order convertToOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(orderDTO.getId());
        order.setCustomer(orderDTO.getCustomer());
        order.setCar(orderDTO.getCar());
        order.setStatus(orderDTO.getStatus());
        order.setDate(orderDTO.getDate());
        return order;
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
