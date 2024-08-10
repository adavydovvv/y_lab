package ru.ylab.service;
import ru.ylab.config.AppConfig;
import ru.ylab.model.Car;
import ru.ylab.out.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private AppConfig appConfig = AppConfig.getInstance();
    private final CarRepository carRespository;

    public CarService(CarRepository carRespository) {
        this.carRespository = carRespository;
    }

    public void addCar(Car car) {
        carRespository.add(car);
        System.out.println("Car added");
    }

    public void updateCarPrice(Car car, int price) {
        carRespository.updateCarPrice(car, price);
    }

    public void removeCar(Car car) {
        carRespository.removeCar(car);
        System.out.println("Car removed");
    }

    public List<String> getCars() {
        List<String> carNames = new ArrayList<>();
        for (Car car : carRespository.getCars()) {
            carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
        }
        return carNames;

    }

    public List<Car> getCarsForTests() {
        return carRespository.getCars();
    }


    public List<String> filterCarsByBrand(String brand) {
        List<String> carNames = new ArrayList<>();
        for (Car car : carRespository.filterCarsByBrand(brand)) {
            carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
        }
        return carNames;
    }

    public List<Car> filterCarsByModel(String model) {
        return carRespository.filterCarsByModel(model);
    }

    public List<Car> filterCarsByYear(int year) {
        return carRespository.filterCarsByYear(year);
    }

    public List<Car> filterCarsByPrice(int price) {
        return carRespository.filterCarsByPrice(price);
    }

    public List<Car> filterCarsByCondition(String condition) {
        return carRespository.filterCarsByCondition(condition);
    }

    public List<Car> filterCarsByEngineType(String engine_type) {
        return carRespository.filterCarsByEngineType(engine_type);
    }
    public void updateCarIsAvailable(Car car, boolean isAvailable){
        carRespository.updateCarIsAvailable(car, isAvailable);
    }

    public int getLastCarId(){
        return carRespository.getLastCarId();
    }

    public Car getCarById(int id) {
        return carRespository.getCarById(id);
    }
}
