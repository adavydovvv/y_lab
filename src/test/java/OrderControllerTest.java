import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.*;
import ru.ylab.out.repository.OrderRepository;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.OrderService;
import ru.ylab.service.UserService;

class OrderControllerTest {

    @Mock
    private List<Order> orders;

    @InjectMocks
    private OrderController orderController;
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orders = new ArrayList<>();
        userController = new UserController(new UserService(new UserRepository()));
        userController.loginUser("manager", "manager");
        Car car = new Car(1, "Toyota", "Camry", 2021, 30000, "White", "New", 1, 250, 2.5, "Gasoline");
        orders.add(new Order(1, AppConfig.getInstance().getAuthorizedUser(), car, OrderStatus.PENDING, 30000, "Oil change", LocalDate.now()));
        orderController = new OrderController(new OrderService(new OrderRepository()));
    }


    @Test
    void testAddOrder() {
        Car car = new Car(2, "Honda", "Accord", 2020, 28000, "Black", "New", 1, 240, 2.0, "Gasoline");
        Order order = new Order(2, AppConfig.getInstance().getAuthorizedUser(), car, OrderStatus.PENDING, 28000, "Tire change", LocalDate.now());
        orderController.addOrder(order);
        assertThat(orderController.getOrdersforTests()).contains(orderController.getOrderById(2));
    }

    @Test
    void testDeleteOrder() {
        Order order = orders.get(0);
        orderController.deleteOrder(order);
        assertThat(orderController.getOrders()).doesNotContain(String.valueOf(order));
    }


    @Test
    void testChangeOrderStatus() {
        Order order = orders.get(0);
        orderController.changeOrderStatus(order, OrderStatus.CONFIRMED);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
    }
}
