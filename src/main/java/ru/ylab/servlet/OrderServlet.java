package ru.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.dto.OrderDTO;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.mapper.OrderMapper;
import ru.ylab.model.Order;
import ru.ylab.model.OrderStatus;
import ru.ylab.out.repository.CarRepository;
import ru.ylab.out.repository.OrderRepository;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.CarService;
import ru.ylab.service.OrderService;
import ru.ylab.service.UserService;
import ru.ylab.utils.ValidationException;
import ru.ylab.validators.OrderDTOValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {

    private final OrderController orderController;
    private final ObjectMapper objectMapper;
    private final OrderDTOValidator orderDTOValidator;
    private final CarController carController;
    private  final UserController userController;

    public OrderServlet() {
        this.carController = new CarController(new CarService(new CarRepository()));
        this.userController = new UserController(new UserService(new UserRepository()));
        this.orderController = new OrderController(new OrderService(new OrderRepository()));
        this.objectMapper = new ObjectMapper();
        this.orderDTOValidator = new OrderDTOValidator();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        OrderDTO orderDTO = objectMapper.readValue(request.getReader(), OrderDTO.class);

        try {
            if (!orderDTOValidator.validate(orderDTO)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Order order = OrderMapper.INSTANCE.orderDTOToOrder(orderDTO);
            orderController.addOrder(order);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, OrderMapper.INSTANCE.orderToOrderDTO(order));
        } catch (ValidationException | SQLException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("Error processing order: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String pathInfo = request.getPathInfo();
        List<String> orders;

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                String status = request.getParameter("status");
                String carId = request.getParameter("carId");
                String username = request.getParameter("username");
                String date = request.getParameter("date");

                if (status != null) {
                    orders = orderController.getOrdersByStatus(OrderStatus.valueOf(status));
                } else if (carId != null) {
                    orders = orderController.getOrdersByCar(carController.getCarById(Integer.parseInt(carId)));
                } else if (username != null) {
                    orders = orderController.filterOrdersByClient(userController.getUserByUsername(username));
                } else if (date != null) {
                    orders = orderController.filterOrdersByDate(Date.valueOf(date).toLocalDate());
                } else {
                    orders = orderController.getOrders();
                }

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(response.getWriter(), orders);
            } else {
                String orderIdStr = extractOrderId(pathInfo);
                if (orderIdStr != null) {
                    Order order = orderController.getOrderById(Integer.parseInt(orderIdStr));
                    response.setContentType("application/json");
                    objectMapper.writeValue(response.getWriter(), OrderMapper.INSTANCE.orderToOrderDTO(order));
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error processing request: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String orderIdStr = extractOrderId(request.getPathInfo());
        if (orderIdStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            orderController.deleteOrder(orderController.getOrderById(Integer.parseInt(orderIdStr)));
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Order not found: " + e.getMessage());
        }
    }

    private String extractOrderId(String pathInfo) {
        return (pathInfo != null && pathInfo.split("/").length > 1) ? pathInfo.split("/")[1] : null;
    }
}
