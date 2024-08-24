import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.ylab.dto.OrderDTO;
import ru.ylab.in.controller.OrderController;
import ru.ylab.model.Order;
import ru.ylab.model.OrderStatus;
import ru.ylab.service.OrderService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrder() throws SQLException {
        OrderDTO orderDTO = new OrderDTO();
        ResponseEntity<Void> response = orderController.addOrder(orderDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(orderService).addOrder(orderDTO);
    }

    @Test
    public void testGetOrders() {
        when(orderService.getOrders()).thenReturn(Collections.singletonList("Order1"));

        ResponseEntity<List<String>> response = orderController.getOrders();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order();
        when(orderService.getOrderById(1)).thenReturn(order);

        ResponseEntity<Void> response = orderController.deleteOrder(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(orderService).deleteOrder(order);
    }

}
