import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ylab.dto.CarDTO;
import ru.ylab.in.controller.CarController;
import ru.ylab.servlet.CarServlet;
import ru.ylab.utils.ValidationException;
import ru.ylab.validators.CarDTOValidator;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CarServletTest {

    private CarServlet carServlet;
    private CarController carController;
    private CarDTOValidator carDTOValidator;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        carController = mock(CarController.class);
        carDTOValidator = mock(CarDTOValidator.class);
        objectMapper = new ObjectMapper();
        carServlet = new CarServlet();
    }

    @Test
    public void testDoPost_Success() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        CarDTO carDTO = new CarDTO();

        carDTO.setBrand("Toyota");
        carDTO.setModel("Camry");
        carDTO.setYear(2022);
        carDTO.setPrice(30000);
        carDTO.setColor("Black");
        carDTO.setCondition("good");
        carDTO.setEngine_capacity(3.3);
        carDTO.setEngine_type("Petrol");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(carDTO))));
        when(carDTOValidator.validate(any())).thenReturn(true);

        doNothing().when(carController).addCar(any());

        carServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write(anyString());
    }

    @Test
    public void testDoPost_ValidationError() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        CarDTO carDTO = new CarDTO();

        carDTO.setBrand("Toyota");
        carDTO.setModel("Camry");
        carDTO.setYear(2022);
        carDTO.setPrice(30000);
        carDTO.setColor("Black");
        carDTO.setCondition("good");
        carDTO.setEngine_capacity(3.3);
        carDTO.setEngine_type("Petrol");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(carDTO))));
        when(carDTOValidator.validate(any())).thenReturn(false);

        carServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer, never()).write(anyString());
    }

}
