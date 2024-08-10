package ru.ylab.model;

import java.sql.Date;
import java.time.LocalDate;

public class Order {
    private int orderId;
    private User customer;
    private Car car;
    private OrderStatus status;
    private double price;
    private Date date;

    private String descriptionOfTheService;

    public Order(int orderId, User customer, Car car, OrderStatus status, Date date) {
        this.orderId = orderId;
        this.customer = customer;
        this.car = car;
        this.status = status;
        this.date = date;
    }

    public Order(int orderId, User customer, Car car, OrderStatus status, double price, String descriptionOfTheService, Date date) {
        this.orderId = orderId;
        this.customer = customer;
        this.car = car;
        this.status = status;
        this.price = price;
        this.descriptionOfTheService = descriptionOfTheService;
        this.date = date;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescriptionOfTheService() {
        return descriptionOfTheService;
    }

    public void setDescriptionOfTheService(String descriptionOfTheService) {
        this.descriptionOfTheService = descriptionOfTheService;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
