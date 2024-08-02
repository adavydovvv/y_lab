package ru.ylab.service;
import ru.ylab.model.Car;
import ru.ylab.model.Role;
import ru.ylab.out.repository.CarRespository;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private UserService userService;
    private final CarRespository carRespository;

    public CarService(CarRespository carRespository) {
        this.carRespository = carRespository;
    }

    public void addCar(Car car) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
            carRespository.add(car);
            System.out.println("Car added");
        }
        else {
            System.out.println("You don't have permission to add a car");
        }
    }

    public void updateCar(Car car, String brand, String model, int year, double price, String color, String condition, int number_of_owners, int horsepower, double engine_capacity, String engine_type) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER){
            carRespository.updateCar(car, brand, model, year, price, color, condition, number_of_owners, horsepower, engine_capacity, engine_type);
        }
        else{
            System.out.println("You cannot change data of cars");
        }
    }

    public void removeCar(Car car) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
            carRespository.removeCar(car);
            System.out.println("Car removed");
        }
        else {
            System.out.println("You don't have permission to remove a car");
        }
    }

    public List<Car> getCars() {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.getCars();
        } else {
            System.out.println("You don't have permission to get list of cars");
            return null;
        }
    }

    public List<Car> filterCarsByBrand(String brand) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
           return carRespository.filterCarsByBrand(brand);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByModel(String model) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByModel(model);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByYear(int year) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByYear(year);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByPrice(double price) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByPrice(price);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByCondition(String condition) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByCondition(condition);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<Car> filterCarsByEngineType(String engine_type) {
        if (userService.getAuthorizedUser().getRole() == Role.MANAGER) {
            return carRespository.filterCarsByEngineType(engine_type);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }
}
