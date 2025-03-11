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

    private static final String ADD_ORDER_SQL = "INSERT INTO carshop.order (id, customerId, carId, status, price, date, descriptionOfTheService) " +
            "VALUES (nextval('public.order_id_seq'), ?, ?, ?, ?, ?, ?)";
    private static final String GET_ORDERS_SQL = "SELECT * FROM carshop.order";
    private static final String CHANGE_ORDER_STATUS_SQL = "UPDATE carshop.order SET status = ? WHERE id = ?";
    private static final String DELETE_ORDER_SQL = "DELETE FROM carshop.order WHERE id = ?";
    private static final String GET_ORDER_BY_ID_SQL = "SELECT * FROM carshop.order WHERE id = ?";

    @Autowired
    public OrderRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository, CarRepository carRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public void addOrder(Order order) {
        jdbcTemplate.update(ADD_ORDER_SQL, order.getCustomer().getUserId(), order.getCar().getId(),
                order.getStatus().toString(), order.getPrice(), order.getDate(), order.getDescriptionOfTheService());
    }

    public List<Order> getOrders() {
        return jdbcTemplate.query(GET_ORDERS_SQL, this::mapRowToOrder);
    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        jdbcTemplate.update(CHANGE_ORDER_STATUS_SQL, status.toString(), order.getOrderId());
    }

    public void deleteOrder(Order order) {
        jdbcTemplate.update(DELETE_ORDER_SQL, order.getOrderId());
    }

    public Order getOrderById(int id) {
        return jdbcTemplate.queryForObject(GET_ORDER_BY_ID_SQL, new Object[]{id}, this::mapRowToOrder);
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
