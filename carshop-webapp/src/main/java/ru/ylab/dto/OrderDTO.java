package ru.ylab.dto;

import jakarta.validation.constraints.NotNull;
import ru.ylab.model.Car;
import ru.ylab.model.OrderStatus;
import ru.ylab.model.User;

import java.sql.Date;

public class OrderDTO {
    private int id;

    @NotNull(message = "Customer is mandatory")
    private User customer;

    @NotNull(message = "Car ID is mandatory")
    private Car car;

    private OrderStatus status;
    private double price;
    private String descriptionOfTheService;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull(message = "Customer is mandatory")
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(@NotNull(message = "Customer is mandatory") User customer) {
        this.customer = customer;
    }

    @NotNull(message = "Car is mandatory")
    public Car getCar() {
        return car;
    }

    public void setCar(@NotNull(message = "Car is mandatory") Car car) {
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
