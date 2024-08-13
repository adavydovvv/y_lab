package ru.ylab.model;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private int price;
    private String color;
    private String condition;
    private int number_of_owners;
    private int horsepower;
    private double engine_capacity;
    private String engine_type;
    private boolean car_available;

    public Car(int id, String brand, String model, int year, int price, String color, String condition, int number_of_owners, int horsepower, double engine_capacity, String engine_type) {
        this.id = id;
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

        setCar_available(true);
    }

    public Car(int id, String brand, String model, int year, int price, String color, String condition, int number_of_owners, int horsepower, double engine_capacity, String engine_type, boolean isCar_available) {
        this.id = id;
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
        this.car_available = isCar_available;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCar_available() {
        return car_available;
    }

    public void setCar_available(boolean car_available) {
        this.car_available = car_available;
    }

}
