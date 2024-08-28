package ru.ylab.out.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ylab.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Autowired
    public OrderRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository, CarRepository carRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public void addOrder(Order order) {
        String sql = "INSERT INTO carshop.order (id, customerId, carId, status, price, date, descriptionOfTheService) " +
                "VALUES (nextval('public.order_id_seq'), ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, order.getCustomer().getUserId(), order.getCar().getId(),
                order.getStatus().toString(), order.getPrice(), order.getDate(), order.getDescriptionOfTheService());
    }

    public List<Order> getOrders() {
        String sql = "SELECT * FROM carshop.order";
        return jdbcTemplate.query(sql, this::mapRowToOrder);
    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        String sql = "UPDATE carshop.order SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status.toString(), order.getOrderId());
    }

    public void deleteOrder(Order order) {
        String sql = "DELETE FROM carshop.order WHERE id = ?";
        jdbcTemplate.update(sql, order.getOrderId());
    }

    public Order getOrderById(int id) {
        String sql = "SELECT * FROM carshop.order WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToOrder);
    }

    public List<Order> filterOrdersByCar(Car car) {
        return getOrders().stream()
                .filter(order -> order.getCar().equals(car))
                .toList();
    }

    public List<Order> filterOrdersByStatus(OrderStatus status) {
        return getOrders().stream()
                .filter(order -> order.getStatus().equals(status))
                .toList();
    }

    public List<Order> filterOrdersByClient(User user) {
        return getOrders().stream()
                .filter(order -> order.getCustomer().equals(user))
                .toList();
    }

    public List<Order> filterOrdersByDate(LocalDate date) {
        return getOrders().stream()
                .filter(order -> order.getDate().toLocalDate().equals(date))
                .toList();
    }

    public int getLastOrderId() {
        return getOrders().size();
    }

    private Order mapRowToOrder(ResultSet rs, int rowNum) throws SQLException {
        return new Order(
                rs.getInt("id"),
                userRepository.getUserById(rs.getInt("customerid")),
                carRepository.getCarById(rs.getInt("carid")),
                OrderStatus.valueOf(rs.getString("status")),
                rs.getDouble("price"),
                rs.getString("descriptionOfTheService"),
                rs.getDate("date")
        );
    }
}
