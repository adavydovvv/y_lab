import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.ylab.in.controller.CarController;
import ru.ylab.model.Car;
import ru.ylab.service.CarService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetCars() {
        when(carService.getCars()).thenReturn(Collections.singletonList("Car1"));

        ResponseEntity<List<String>> response = carController.getCars();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testRemoveCar() {
        Car car = new Car();
        when(carService.getCarById(1)).thenReturn(car);

        ResponseEntity<Void> response = carController.removeCar(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(carService).removeCar(car);
    }



}
