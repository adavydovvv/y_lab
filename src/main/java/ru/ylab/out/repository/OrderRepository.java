package ru.ylab.out.repository;

import ru.ylab.model.*;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private UserRepository userRepository = new UserRepository();
    private CarRepository carRepository = new CarRepository();

    public void addOrder(Order order) throws SQLException {

        String sql = "INSERT INTO carshop.order (id, customerId, carId, status, price, date, descriptionOfTheService) " +
                "VALUES (nextval('public.order_id_seq'), ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getCustomer().getUserId());
            stmt.setInt(2, order.getCar().getId());
            stmt.setString(3, order.getStatus().toString());
            stmt.setDouble(4, order.getPrice());
            stmt.setDate(5, order.getDate());
            stmt.setString(6, order.getDescriptionOfTheService());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when adding an order: " + e.getMessage());
        }

    }
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM carshop.order";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order(rs.getInt("id"), userRepository.getUserById(rs.getInt("customerid")), carRepository.getCarById(rs.getInt("carid")), OrderStatus.valueOf(rs.getString("status")), rs.getDouble("price"), rs.getString("descriptionOfTheService"), rs.getDate("date"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }

        return orders;
    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        String sql = "UPDATE carshop.order SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());
            stmt.setInt(2, order.getOrderId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Order not found or no changes made");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }
    }

    public void deleteOrder(Order order) {
        String sql = "DELETE FROM carshop.order WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getOrderId());
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }
    }

    public Order getOrderById(int id) {
        return getOrders().stream()
                .filter(order -> order.getOrderId() == id)
                .findFirst()
                .orElse(null);
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
                .filter(order -> order.getDate().equals(date))
                .toList();
    }

    public int getLastOrderId() {
        return getOrders().size();
    }
}
