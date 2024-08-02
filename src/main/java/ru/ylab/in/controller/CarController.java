package ru.ylab.in.controller;

import ru.ylab.model.Car;
import ru.ylab.service.CarService;

import java.util.List;

public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    public void addCar(Car car) {
        carService.addCar(car);
    }

    public void updateCar(Car car, String brand, String model, int year, double price, String color, String condition, int number_of_owners, int horsepower, double engine_capacity, String engine_type){
        carService.updateCar(car, brand, model, year, price, color, condition, number_of_owners, horsepower, engine_capacity, engine_type);
    }

    public void removeCar(Car car) {
        carService.removeCar(car);
    }

    public List<Car> getCars() {
        return carService.getCars();
    }

    public List<Car> filterCarsByBrand(String brand) {
        return carService.filterCarsByBrand(brand);
    }

    public List<Car> filterCarsByModel(String model) {
        return carService.filterCarsByModel(model);
    }

    public List<Car> filterCarsByYear(int year) {
        return carService.filterCarsByYear(year);
    }

    public List<Car> filterCarsByPrice(double price) {
        return carService.filterCarsByPrice(price);
    }

    public List<Car> filterCarsByCondition(String condition) {
        return carService.filterCarsByCondition(condition);
    }

    public List<Car> filterCarsByEngineType(String engine_type) {
        return carService.filterCarsByEngineType(engine_type);
    }
}
