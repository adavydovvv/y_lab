import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ylab.config.AppConfig;
import ru.ylab.in.controller.OrderController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.Car;
import ru.ylab.model.Order;
import ru.ylab.model.OrderStatus;
import ru.ylab.out.repository.OrderRepository;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.OrderService;
import ru.ylab.service.UserService;

@Testcontainers
public class OrderControllerTest extends AbstractIntegrationTest {
    @InjectMocks
    private OrderController orderController;
    private UserController userController;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(new UserService(new UserRepository()));
        userController.loginUser("manager", "manager");

        Car car = new Car(1, "Toyota", "Camry", 2021, 30000, "White", "New", 1, 250, 2.5, "Gasoline");
        orderController = new OrderController(new OrderService(new OrderRepository()));
        orderController.addOrder(new Order(1, AppConfig.getInstance().getAuthorizedUser(), car, OrderStatus.PENDING, 30000, "Oil change", Date.valueOf(LocalDate.now())));
    }

    @Test
    void testAddOrder() throws SQLException {
        Car car = new Car(2, "Honda", "Accord", 2020, 28000, "Black", "New", 1, 240, 2.0, "Gasoline");
        Order order = new Order(2, AppConfig.getInstance().getAuthorizedUser(), car, OrderStatus.PENDING, 28000, "Tire change", Date.valueOf(LocalDate.now()));
        orderController.addOrder(order);
        assertThat(orderController.getOrdersforTests()).contains(orderController.getOrderById(2));
    }

    @Test
    void testDeleteOrder() {
        Order order = orderController.getOrderById(1);
        orderController.deleteOrder(order);
        assertThat(orderController.getOrdersforTests()).doesNotContain(order);
    }

    @Test
    void testChangeOrderStatus() {
        Order order = orderController.getOrderById(1);
        orderController.changeOrderStatus(order, OrderStatus.CONFIRMED);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
    }
}
