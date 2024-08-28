package ru.ylab.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylab.model.Car;
import ru.ylab.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        carService.addCar(car);
        return ResponseEntity.ok(car);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Void> updateCarPrice(@PathVariable int id, @RequestBody int price) {
        Car car = carService.getCarById(id);
        carService.updateCarPrice(car, price);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCar(@PathVariable int id) {
        Car car = carService.getCarById(id);
        carService.removeCar(car);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<String>> getCars() {
        return ResponseEntity.ok(carService.getCars());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<String>> filterCarsByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(carService.filterCarsByBrand(brand));
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<List<String>> filterCarsByModel(@PathVariable String model) {
        return ResponseEntity.ok(carService.filterCarsByModel(model));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<String>> filterCarsByYear(@PathVariable int year) {
        return ResponseEntity.ok(carService.filterCarsByYear(year));
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<String>> filterCarsByPrice(@PathVariable int price) {
        return ResponseEntity.ok(carService.filterCarsByPrice(price));
    }

    @GetMapping("/condition/{condition}")
    public ResponseEntity<List<String>> filterCarsByCondition(@PathVariable String condition) {
        return ResponseEntity.ok(carService.filterCarsByCondition(condition));
    }

    @GetMapping("/engine-type/{engineType}")
    public ResponseEntity<List<String>> filterCarsByEngineType(@PathVariable String engineType) {
        return ResponseEntity.ok(carService.filterCarsByEngineType(engineType));
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<Void> updateCarIsAvailable(@PathVariable int id, @RequestBody boolean isAvailable) {
        Car car = carService.getCarById(id);
        carService.updateCarIsAvailable(car, isAvailable);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/last-id")
    public ResponseEntity<Integer> getLastCarId() {
        return ResponseEntity.ok(carService.getLastCarId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable int id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @GetMapping("/tests")
    public ResponseEntity<List<Car>> getCarsForTests() {
        return ResponseEntity.ok(carService.getCarsForTests());
    }
}
