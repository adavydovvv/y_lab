package ru.ylab.out.repository;

import ru.ylab.model.Car;
import ru.ylab.out.data.InMemoryDatabase;
import java.util.List;

public class CarRepository {

    private List<Car> cars = InMemoryDatabase.getInstance().getCars();

    public void add(Car car) {
        cars.add(car);
    }

    public void updateCarPrice(Car car, int price){
        car.setPrice(price);
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

    public List<Car> filterCarsByPrice(int price) {
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

    public int getLastCarId(){
        return cars.size();
    }

    public Car getCarById(int id) {
        for (Car car : cars) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

}

