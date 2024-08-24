package ru.ylab.out.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ylab.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CarRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Car car) {
        String sql = "INSERT INTO carshop.car (id, brand, model, year, price, color, condition, number_of_owners, horsepower, engine_capacity, engine_type, car_available) " +
                "VALUES (nextval('public.car_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, car.getBrand(), car.getModel(), car.getYear(), car.getPrice(), car.getColor(),
                car.getCondition(), car.getNumber_of_owners(), car.getHorsepower(), car.getEngine_capacity(),
                car.getEngine_type(), car.isCar_available());
    }

    public void updateCarPrice(Car car, int price) {
        String sql = "UPDATE carshop.car SET price = ? WHERE id = ?";
        jdbcTemplate.update(sql, price, car.getId());
    }

    public void updateCarIsAvailable(Car car, boolean isAvailable) {
        String sql = "UPDATE carshop.car SET car_available = ? WHERE id = ?";
        jdbcTemplate.update(sql, isAvailable, car.getId());
    }

    public void removeCar(Car car) {
        String sql = "DELETE FROM carshop.car WHERE id = ?";
        jdbcTemplate.update(sql, car.getId());
    }

    public List<Car> getCars() {
        String sql = "SELECT * FROM carshop.car";
        return jdbcTemplate.query(sql, this::mapRowToCar);
    }

    public List<Car> filterCarsByBrand(String brand) {
        String sql = "SELECT * FROM carshop.car WHERE brand = ?";
        return jdbcTemplate.query(sql, new Object[]{brand}, this::mapRowToCar);
    }

    public List<Car> filterCarsByModel(String model) {
        String sql = "SELECT * FROM carshop.car WHERE model = ?";
        return jdbcTemplate.query(sql, new Object[]{model}, this::mapRowToCar);
    }

    public List<Car> filterCarsByYear(int year) {
        String sql = "SELECT * FROM carshop.car WHERE year = ?";
        return jdbcTemplate.query(sql, new Object[]{year}, this::mapRowToCar);
    }

    public List<Car> filterCarsByPrice(int price) {
        String sql = "SELECT * FROM carshop.car WHERE price = ?";
        return jdbcTemplate.query(sql, new Object[]{price}, this::mapRowToCar);
    }

    public List<Car> filterCarsByCondition(String condition) {
        String sql = "SELECT * FROM carshop.car WHERE condition = ?";
        return jdbcTemplate.query(sql, new Object[]{condition}, this::mapRowToCar);
    }

    public List<Car> filterCarsByEngineType(String engineType) {
        String sql = "SELECT * FROM carshop.car WHERE engine_type = ?";
        return jdbcTemplate.query(sql, new Object[]{engineType}, this::mapRowToCar);
    }

    public int getLastCarId() {
        String sql = "SELECT COUNT(*) FROM carshop.car";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Car getCarById(int id) {
        String sql = "SELECT * FROM carshop.car WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToCar);
    }

    private Car mapRowToCar(ResultSet rs, int rowNum) throws SQLException {
        return new Car(
                rs.getInt("id"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getInt("price"),
                rs.getString("color"),
                rs.getString("condition"),
                rs.getInt("number_of_owners"),
                rs.getInt("horsepower"),
                rs.getDouble("engine_capacity"),
                rs.getString("engine_type"),
                rs.getBoolean("car_available")
        );
    }
}
