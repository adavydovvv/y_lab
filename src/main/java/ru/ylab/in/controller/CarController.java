package ru.ylab.in.controller;

import ru.ylab.model.Car;
import ru.ylab.model.Role;
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

    public void updateCarPrice(Car car, int price){
        carService.updateCarPrice(car, price);
    }

    public void removeCar(Car car) {
        carService.removeCar(car);
    }

    public List<String> getCars() {
        return carService.getCars();
    }

    public List<String> filterCarsByBrand(String brand) {
        return carService.filterCarsByBrand(brand);
    }

    public List<Car> filterCarsByModel(String model) {
        return carService.filterCarsByModel(model);
    }

    public List<Car> filterCarsByYear(int year) {
        return carService.filterCarsByYear(year);
    }


    public List<Car> filterCarsByPrice(int price) {
        return carService.filterCarsByPrice(price);
    }

    public List<Car> filterCarsByCondition(String condition) {
        return carService.filterCarsByCondition(condition);
    }

    public List<Car> filterCarsByEngineType(String engine_type) {
        return carService.filterCarsByEngineType(engine_type);
    }

    public int getLastCarId(){
        return carService.getLastCarId();
    }

    public Car getCarById(int id) {
        return carService.getCarById(id);
    }
    public List<Car> getCarsForTests() {
        return carService.getCarsForTests();
    }
}
