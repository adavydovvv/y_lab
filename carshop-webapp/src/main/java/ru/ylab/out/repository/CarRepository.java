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

    private static final String ADD_CAR_SQL = "INSERT INTO carshop.car (id, brand, model, year, price, color, condition, number_of_owners, horsepower, engine_capacity, engine_type, car_available) " +
            "VALUES (nextval('public.car_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CAR_PRICE_SQL = "UPDATE carshop.car SET price = ? WHERE id = ?";
    private static final String UPDATE_CAR_AVAILABILITY_SQL = "UPDATE carshop.car SET car_available = ? WHERE id = ?";
    private static final String REMOVE_CAR_SQL = "DELETE FROM carshop.car WHERE id = ?";
    private static final String GET_CARS_SQL = "SELECT * FROM carshop.car";
    private static final String FILTER_CARS_BY_BRAND_SQL = "SELECT * FROM carshop.car WHERE brand = ?";
    private static final String FILTER_CARS_BY_MODEL_SQL = "SELECT * FROM carshop.car WHERE model = ?";
    private static final String FILTER_CARS_BY_YEAR_SQL = "SELECT * FROM carshop.car WHERE year = ?";
    private static final String FILTER_CARS_BY_PRICE_SQL = "SELECT * FROM carshop.car WHERE price = ?";
    private static final String FILTER_CARS_BY_CONDITION_SQL = "SELECT * FROM carshop.car WHERE condition = ?";
    private static final String FILTER_CARS_BY_ENGINE_TYPE_SQL = "SELECT * FROM carshop.car WHERE engine_type = ?";
    private static final String GET_LAST_CAR_ID_SQL = "SELECT COUNT(*) FROM carshop.car";
    private static final String GET_CAR_BY_ID_SQL = "SELECT * FROM carshop.car WHERE id = ?";

    @Autowired
    public CarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Car car) {
        jdbcTemplate.update(ADD_CAR_SQL, car.getBrand(), car.getModel(), car.getYear(), car.getPrice(), car.getColor(),
                car.getCondition(), car.getNumber_of_owners(), car.getHorsepower(), car.getEngine_capacity(),
                car.getEngine_type(), car.isCar_available());
    }

    public void updateCarPrice(Car car, int price) {
        jdbcTemplate.update(UPDATE_CAR_PRICE_SQL, price, car.getId());
    }

    public void updateCarIsAvailable(Car car, boolean isAvailable) {
        jdbcTemplate.update(UPDATE_CAR_AVAILABILITY_SQL, isAvailable, car.getId());
    }

    public void removeCar(Car car) {
        jdbcTemplate.update(REMOVE_CAR_SQL, car.getId());
    }

    public List<Car> getCars() {
        return jdbcTemplate.query(GET_CARS_SQL, this::mapRowToCar);
    }

    public List<Car> filterCarsByBrand(String brand) {
        return jdbcTemplate.query(FILTER_CARS_BY_BRAND_SQL, new Object[]{brand}, this::mapRowToCar);
    }

    public List<Car> filterCarsByModel(String model) {
        return jdbcTemplate.query(FILTER_CARS_BY_MODEL_SQL, new Object[]{model}, this::mapRowToCar);
    }

    public List<Car> filterCarsByYear(int year) {
        return jdbcTemplate.query(FILTER_CARS_BY_YEAR_SQL, new Object[]{year}, this::mapRowToCar);
    }

    public List<Car> filterCarsByPrice(int price) {
        return jdbcTemplate.query(FILTER_CARS_BY_PRICE_SQL, new Object[]{price}, this::mapRowToCar);
    }

    public List<Car> filterCarsByCondition(String condition) {
        return jdbcTemplate.query(FILTER_CARS_BY_CONDITION_SQL, new Object[]{condition}, this::mapRowToCar);
    }

    public List<Car> filterCarsByEngineType(String engineType) {
        return jdbcTemplate.query(FILTER_CARS_BY_ENGINE_TYPE_SQL, new Object[]{engineType}, this::mapRowToCar);
    }

    public int getLastCarId() {
        return jdbcTemplate.queryForObject(GET_LAST_CAR_ID_SQL, Integer.class);
    }

    public Car getCarById(int id) {
        return jdbcTemplate.queryForObject(GET_CAR_BY_ID_SQL, new Object[]{id}, this::mapRowToCar);
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
