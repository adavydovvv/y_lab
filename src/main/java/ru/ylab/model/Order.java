package ru.ylab.model;

public class Order {
    private int orderId;
    private User customer;
    private Car car;
    private OrderStatus status;

    public Order(int orderId, User customer, Car car, OrderStatus status) {
        this.orderId = orderId;
        this.customer = customer;
        this.car = car;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
