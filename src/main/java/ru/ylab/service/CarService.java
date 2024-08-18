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

    public List<String> filterCarsByModel(String model) {
        List<String> carNames = new ArrayList<>();
        for (Car car : carRespository.filterCarsByModel(model)) {
            carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
        }
        return carNames;
    }

    public List<String> filterCarsByYear(int year) {
        List<String> carNames = new ArrayList<>();
        for (Car car : carRespository.filterCarsByYear(year)) {
            carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
        }
        return carNames;
    }

    public List<String> filterCarsByPrice(int price) {
        List<String> carNames = new ArrayList<>();
        for (Car car : carRespository.filterCarsByPrice(price)) {
            carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
        }
        return carNames;
    }

    public List<String> filterCarsByCondition(String condition) {
        List<String> carNames = new ArrayList<>();
        for (Car car : carRespository.filterCarsByCondition(condition)) {
            carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
        }
        return carNames;
    }

    public List<String> filterCarsByEngineType(String engine_type) {
        List<String> carNames = new ArrayList<>();
        for (Car car : carRespository.filterCarsByEngineType(engine_type)) {
            carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
        }
        return carNames;
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
