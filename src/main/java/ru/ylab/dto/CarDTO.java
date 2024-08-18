package ru.ylab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarDTO {
    private int id;

    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotBlank(message = "Model is mandatory")
    private String model;

    @NotNull(message = "Year is mandatory")
    private int year;

    @NotNull(message = "Price is mandatory")
    private int price;

    private String color;
    private String condition;
    private int number_of_owners;
    private int horsepower;
    private double engine_capacity;
    private String engine_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "Brand is mandatory") String getBrand() {
        return brand;
    }

    public void setBrand(@NotBlank(message = "Brand is mandatory") String brand) {
        this.brand = brand;
    }

    public @NotBlank(message = "Model is mandatory") String getModel() {
        return model;
    }

    public void setModel(@NotBlank(message = "Model is mandatory") String model) {
        this.model = model;
    }

    @NotNull(message = "Year is mandatory")
    public int getYear() {
        return year;
    }

    public void setYear(@NotNull(message = "Year is mandatory") int year) {
        this.year = year;
    }

    @NotNull(message = "Price is mandatory")
    public int getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price is mandatory") int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getNumber_of_owners() {
        return number_of_owners;
    }

    public void setNumber_of_owners(int number_of_owners) {
        this.number_of_owners = number_of_owners;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public double getEngine_capacity() {
        return engine_capacity;
    }

    public void setEngine_capacity(double engine_capacity) {
        this.engine_capacity = engine_capacity;
    }

    public String getEngine_type() {
        return engine_type;
    }

    public void setEngine_type(String engine_type) {
        this.engine_type = engine_type;
    }
}
