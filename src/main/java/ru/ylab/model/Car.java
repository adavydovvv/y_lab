package ru.ylab.model;

public class Car {
    private String brand;
    private String model;
    private int year;
    private double price;
    private String color;
    private String condition;
    private int number_of_owners;
    private int horsepower;
    private double engine_capacity;
    private String engine_type;

    public Car(String brand, String model, int year, double price, String color, String condition, int number_of_owners, int horsepower, double engine_capacity, String engine_type) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.color = color;
        this.condition = condition;
        this.number_of_owners = number_of_owners;
        this.horsepower = horsepower;
        this.engine_capacity = engine_capacity;
        this.engine_type = engine_type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
