package ru.ylab.in.menu;

import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.AuditController;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.out.repository.AuditRepository;
import ru.ylab.out.repository.CarRepository;
import ru.ylab.out.repository.OrderRepository;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.AuditService;
import ru.ylab.service.CarService;
import ru.ylab.service.OrderService;
import ru.ylab.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class MainDisplay {
    public static void display () throws InterruptedException, SQLException {
        AppConfig appConfig = AppConfig.getInstance();

        UserRepository userRepository = new UserRepository();
        CarRepository carRepository = new CarRepository();
        OrderRepository orderRepository = new OrderRepository();
        AuditRepository auditRepository = new AuditRepository();

        UserService userService = new UserService(userRepository);
        CarService carService = new CarService(carRepository);
        OrderService orderService = new OrderService(orderRepository);
        AuditService auditService = new AuditService(auditRepository);

        UserController userController = new UserController(userService);
        CarController carController = new CarController(carService);
        OrderController orderController = new OrderController(orderService);
        AuditController auditController = new AuditController(auditService);

        Scanner scanner = new Scanner(System.in);

        MainMenu.display(scanner, userController, carController, orderController, auditController);
    }
}
