package ru.ylab.service;
import ru.ylab.config.AppConfig;
import ru.ylab.model.Car;
import ru.ylab.model.Role;
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
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            carRespository.add(car);
            System.out.println("Car added");
        }
        else {
            System.out.println("You don't have permission to add a car");
        }
    }

    public void updateCarPrice(Car car, int price) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER){
            carRespository.updateCarPrice(car, price);
        }
        else{
            System.out.println("You cannot change data of cars");
        }
    }

    public void removeCar(Car car) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            carRespository.removeCar(car);
            System.out.println("Car removed");
        }
        else {
            System.out.println("You don't have permission to remove a car");
        }
    }

    public List<String> getCars() {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            List<String> carNames = new ArrayList<>();
            for (Car car : carRespository.getCars()) {
                carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
            }
            return carNames;
        } else {
            System.out.println("You don't have permission to get list of cars");
            return null;
        }
    }

    public List<Car> getCarsForTests() {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.getCars();
        } else {
            System.out.println("You don't have permission to get list of cars");
            return null;
        }
    }


    public List<String> filterCarsByBrand(String brand) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            List<String> carNames = new ArrayList<>();
            for (Car car : carRespository.filterCarsByBrand(brand)) {
                carNames.add("ID: " + car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getColor() + ", Price: " + car.getPrice());
            }
            return carNames;
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByModel(String model) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByModel(model);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByYear(int year) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByYear(year);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByPrice(int price) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByPrice(price);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByCondition(String condition) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByCondition(condition);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByEngineType(String engine_type) {
        if (appConfig.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByEngineType(engine_type);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public int getLastCarId(){
        return carRespository.getLastCarId();
    }

    public Car getCarById(int id) {
        return carRespository.getCarById(id);
    }
}
