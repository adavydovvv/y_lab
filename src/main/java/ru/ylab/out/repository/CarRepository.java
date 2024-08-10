package ru.ylab.out.repository;

import ru.ylab.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    public void add(Car car) {
        String sql = "INSERT INTO carshop.car (id, brand, model, year, price, color, condition, number_of_owners, horsepower, engine_capacity, engine_type, car_available) " +
                "VALUES (nextval('public.car_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setInt(4, car.getPrice());
            stmt.setString(5, car.getColor());
            stmt.setString(6, car.getCondition());
            stmt.setInt(7, car.getNumber_of_owners());
            stmt.setInt(8, car.getHorsepower());
            stmt.setDouble(9, car.getEngine_capacity());
            stmt.setString(10, car.getEngine_type());
            stmt.setBoolean(11, car.isCar_available());


            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when adding a car: " + e.getMessage());
        }
    }

    public void updateCarPrice(Car car, int price){
        String sql = "UPDATE carshop.car SET price = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, price);
            stmt.setInt(2, car.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Car price updated to " + price);
            }
            else {
                System.out.println("Car price not updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }
    }

    public void updateCarIsAvailable(Car car, boolean isAvailable){
        String sql = "UPDATE carshop.car SET car_available = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isAvailable);
            stmt.setInt(2, car.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Car available status changed to " + isAvailable);
            }
            else {
                System.out.println("Car available status not updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }
    }

    public void removeCar(Car car) {
        String sql = "DELETE FROM carshop.car WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, car.getId());
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }
    }

    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM carshop.car";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car(rs.getInt("id"), rs.getString("brand"), rs.getString("model"), rs.getInt("year"), rs.getInt("price"), rs.getString("color"), rs.getString("condition"), rs.getInt("number_of_owners"), rs.getInt("horsepower"), rs.getInt("engine_capacity"), rs.getString("engine_type"), rs.getBoolean("car_available"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }

        return cars;
    }

    public List<Car> filterCarsByBrand(String brand) {
        return getCars().stream()
                .filter(cars -> cars.getBrand().equals(brand))
                .toList();
    }


    public List<Car> filterCarsByModel(String model) {
        return getCars().stream()
                .filter(cars -> cars.getBrand().equals(model))
                .toList();

    }

    public List<Car> filterCarsByYear(int year) {
        return getCars().stream()
                .filter(cars -> cars.getYear() == year)
                .toList();

    }

    public List<Car> filterCarsByPrice(int price) {
        return getCars().stream()
                .filter(cars -> cars.getPrice() == price)
                .toList();

    }

    public List<Car> filterCarsByCondition(String condition) {
        return getCars().stream()
                .filter(cars -> cars.getCondition().equals(condition))
                .toList();
    }

    public List<Car> filterCarsByEngineType(String engine_type) {
        return getCars().stream()
                .filter(cars -> cars.getEngine_type().equals(engine_type))
                .toList();
    }

    public int getLastCarId(){
        return getCars().size();
    }

    public Car getCarById(int id) {
        for (Car car : getCars()) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

}

