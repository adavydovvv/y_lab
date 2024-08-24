package ru.ylab.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylab.dto.OrderDTO;
import ru.ylab.model.Car;
import ru.ylab.model.Order;
import ru.ylab.model.OrderStatus;
import ru.ylab.model.User;
import ru.ylab.service.OrderService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> addOrder(@RequestBody OrderDTO orderDTO) throws SQLException {
        orderService.addOrder(orderDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable int id, @RequestBody OrderStatus status) {
        Order order = orderService.getOrderById(id);
        orderService.changeOrderStatus(order, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<String>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        Order order = orderService.getOrderById(id);
        orderService.deleteOrder(order);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<String>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @GetMapping("/car")
    public ResponseEntity<List<String>> getOrdersByCar(@RequestBody Car car) {
        return ResponseEntity.ok(orderService.getOrdersByCar(car));
    }

    @GetMapping("/client")
    public ResponseEntity<List<String>> filterOrdersByClient(@RequestBody User user) {
        return ResponseEntity.ok(orderService.filterOrdersByClient(user));
    }

    @GetMapping("/last-id")
    public ResponseEntity<Integer> getLastOrderId() {
        return ResponseEntity.ok(orderService.getLastOrderId());
    }

    @GetMapping("/date")
    public ResponseEntity<List<String>> filterOrdersByDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(orderService.filterOrdersByDate(date));
    }

}
