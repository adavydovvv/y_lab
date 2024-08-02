package ru.ylab.out.repository;

import ru.ylab.model.Car;
import ru.ylab.model.Role;

import java.util.ArrayList;
import java.util.List;

public class CarRespository {

    private List<Car> cars = new ArrayList<>();

    public void add(Car car) {
        cars.add(car);
    }

    public void updateCar(Car car, String brand, String model, int year, double price, String color, String condition, int number_of_owners, int horsepower, double engine_capacity, String engine_type){
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPrice(price);
        car.setColor(color);
        car.setCondition(condition);
        car.setNumber_of_owners(number_of_owners);
        car.setHorsepower(horsepower);
        car.setEngine_capacity(engine_capacity);
        car.setEngine_type(engine_type);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Car> filterCarsByBrand(String brand) {
        return cars.stream()
                .filter(cars -> cars.getBrand().equals(brand))
                .toList();

    }

    public List<Car> filterCarsByModel(String model) {
        return cars.stream()
                .filter(cars -> cars.getBrand().equals(model))
                .toList();

    }

    public List<Car> filterCarsByYear(int year) {
        return cars.stream()
                .filter(cars -> cars.getYear() == year)
                .toList();

    }

    public List<Car> filterCarsByPrice(double price) {
        return cars.stream()
                .filter(cars -> cars.getPrice() == price)
                .toList();

    }

    public List<Car> filterCarsByCondition(String condition) {
        return cars.stream()
                .filter(cars -> cars.getCondition().equals(condition))
                .toList();
    }

    public List<Car> filterCarsByEngineType(String engine_type) {
        return cars.stream()
                .filter(cars -> cars.getEngine_type().equals(engine_type))
                .toList();
    }
}

