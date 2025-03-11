

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ylab.application.CarShopApplication;
import ru.ylab.dto.OrderDTO;
import ru.ylab.in.controller.OrderController;
import ru.ylab.model.Order;
import ru.ylab.model.OrderStatus;
import ru.ylab.service.OrderService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CarShopApplication.class)
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void addOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk());

        verify(orderService, times(1)).addOrder(orderDTO);
    }

    @Test
    void changeOrderStatus() throws Exception {
        int orderId = 1;
        OrderStatus status = OrderStatus.CONFIRMED;
        Order order = new Order();
        when(orderService.getOrderById(orderId)).thenReturn(order);

        mockMvc.perform(put("/api/orders/{id}/status", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(status)))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).changeOrderStatus(order, status);
    }

    @Test
    void getOrders() throws Exception {
        List<String> orders = Collections.singletonList("Order1");
        when(orderService.getOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("Order1"));
    }

    @Test
    void deleteOrder() throws Exception {
        int orderId = 1;
        Order order = new Order();
        when(orderService.getOrderById(orderId)).thenReturn(order);

        mockMvc.perform(delete("/api/orders/{id}", orderId))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(order);
    }

    @Test
    void getOrderById() throws Exception {
        int orderId = 1;
        Order order = new Order();
        when(orderService.getOrderById(orderId)).thenReturn(order);

        mockMvc.perform(get("/api/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void getOrdersByStatus() throws Exception {
        OrderStatus status = OrderStatus.PENDING;
        List<String> orders = Collections.singletonList("Order1");
        when(orderService.getOrdersByStatus(status)).thenReturn(orders);

        mockMvc.perform(get("/api/orders/status/{status}", status))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("Order1"));

        verify(orderService, times(1)).getOrdersByStatus(status);
    }

    @Test
    void filterOrdersByDate() throws Exception {
        LocalDate date = LocalDate.now();
        List<String> orders = Collections.singletonList("Order1");
        when(orderService.filterOrdersByDate(date)).thenReturn(orders);

        mockMvc.perform(get("/api/orders/date")
                        .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("Order1"));

        verify(orderService, times(1)).filterOrdersByDate(date);
    }

    @Test
    void getLastOrderId() throws Exception {
        int lastOrderId = 42;
        when(orderService.getLastOrderId()).thenReturn(lastOrderId);

        mockMvc.perform(get("/api/orders/last-id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(lastOrderId));

        verify(orderService, times(1)).getLastOrderId();
    }
}
