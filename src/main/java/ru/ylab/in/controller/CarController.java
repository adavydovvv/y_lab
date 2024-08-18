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

    public List<String> filterCarsByModel(String model) {
        return carService.filterCarsByModel(model);
    }

    public List<String> filterCarsByYear(int year) {
        return carService.filterCarsByYear(year);
    }


    public List<String> filterCarsByPrice(int price) {
        return carService.filterCarsByPrice(price);
    }

    public List<String> filterCarsByCondition(String condition) {
        return carService.filterCarsByCondition(condition);
    }

    public List<String> filterCarsByEngineType(String engine_type) {
        return carService.filterCarsByEngineType(engine_type);
    }

    public void updateCarIsAvailable(Car car, boolean isAvailable){
        carService.updateCarIsAvailable(car, isAvailable);
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
