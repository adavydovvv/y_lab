package ru.ylab.dto;

import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;

public class OrderDTO {
    private int id;

    @NotNull(message = "Customer ID is mandatory")
    private int customerId;

    @NotNull(message = "Car ID is mandatory")
    private int carId;

    private String status;
    private double price;
    private String descriptionOfTheService;
    private LocalDate date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull(message = "Customer ID is mandatory")
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(@NotNull(message = "Customer ID is mandatory") int customerId) {
        this.customerId = customerId;
    }

    @NotNull(message = "Car ID is mandatory")
    public int getCarId() {
        return carId;
    }

    public void setCarId(@NotNull(message = "Car ID is mandatory") int carId) {
        this.carId = carId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
