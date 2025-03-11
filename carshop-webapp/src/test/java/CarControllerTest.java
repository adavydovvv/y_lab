
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
import ru.ylab.in.controller.CarController;
import ru.ylab.model.Car;
import ru.ylab.service.CarService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CarShopApplication.class)
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }


    @Test
    void removeCar() throws Exception {
        int carId = 1;
        Car car = new Car();
        when(carService.getCarById(carId)).thenReturn(car);

        mockMvc.perform(delete("/api/cars/{id}", carId))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).removeCar(car);
    }

    @Test
    void getCars() throws Exception {
        List<String> cars = Collections.singletonList("Car1");
        when(carService.getCars()).thenReturn(cars);

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getCarById() throws Exception {
        int carId = 1;
        Car car = new Car();
        when(carService.getCarById(carId)).thenReturn(car);

        mockMvc.perform(get("/api/cars/{id}", carId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(carService, times(1)).getCarById(carId);
    }

    @Test
    void filterCarsByPrice() throws Exception {
        int price = 20000;
        List<String> filteredCars = Collections.singletonList("Car1");
        when(carService.filterCarsByPrice(price)).thenReturn(filteredCars);

        mockMvc.perform(get("/api/cars/price/{price}", price))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(carService, times(1)).filterCarsByPrice(price);
    }

    @Test
    void filterCarsByCondition() throws Exception {
        String condition = "new";
        List<String> filteredCars = Collections.singletonList("Car2");
        when(carService.filterCarsByCondition(condition)).thenReturn(filteredCars);

        mockMvc.perform(get("/api/cars/condition/{condition}", condition))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(carService, times(1)).filterCarsByCondition(condition);
    }

    @Test
    void filterCarsByEngineType() throws Exception {
        String engineType = "diesel";
        List<String> filteredCars = Collections.singletonList("Car3");
        when(carService.filterCarsByEngineType(engineType)).thenReturn(filteredCars);

        mockMvc.perform(get("/api/cars/engine-type/{engineType}", engineType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(carService, times(1)).filterCarsByEngineType(engineType);
    }

    @Test
    void updateCarIsAvailable() throws Exception {
        int carId = 1;
        boolean isAvailable = true;
        Car car = new Car();
        when(carService.getCarById(carId)).thenReturn(car);

        mockMvc.perform(put("/api/cars/{id}/availability", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(isAvailable)))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).updateCarIsAvailable(car, isAvailable);
    }
}
