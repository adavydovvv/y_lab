import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.OrderDTO;
import ru.ylab.in.controller.OrderController;
import ru.ylab.model.OrderStatus;
import ru.ylab.servlet.OrderServlet;
import ru.ylab.validators.OrderDTOValidator;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServletTest {

    private OrderServlet orderServlet;
    private OrderController orderController;
    private OrderDTOValidator orderDTOValidator;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        orderController = mock(OrderController.class);
        orderDTOValidator = mock(OrderDTOValidator.class);
        objectMapper = new ObjectMapper();
        orderServlet = new OrderServlet();
    }

    @Test
    public void testDoPost_Success() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setCarId(1);
        orderDTO.setCustomerId(4);
        orderDTO.setStatus(String.valueOf(OrderStatus.PENDING));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(orderDTO))));
        when(orderDTOValidator.validate(any())).thenReturn(true);

        doNothing().when(orderController).addOrder(any());

        orderServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write(anyString());
    }


    @Test
    public void testDoPost_ValidationError() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        OrderDTO orderDTO = new OrderDTO();

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(orderDTO))));
        when(orderDTOValidator.validate(any())).thenReturn(false);

        orderServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

}
