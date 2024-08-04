import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.Car;
import ru.ylab.out.repository.CarRepository;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.CarService;
import ru.ylab.service.UserService;

class CarControllerTest {

    @Mock
    private List<Car> cars;

    @InjectMocks
    private CarController carController;
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cars = new ArrayList<>();
        cars.add(new Car(1, "Toyota", "Camry", 2021, 30000, "White", "New", 1, 250, 2.5, "Gasoline"));
        cars.add(new Car(2, "Honda", "Accord", 2020, 28000, "Black", "New", 1, 240, 2.0, "Gasoline"));
        carController = new CarController(new CarService(new CarRepository()));
        userController = new UserController(new UserService(new UserRepository()));
        userController.loginUser("manager", "manager");
    }


    @Test
    void testAddCar() {
        Car car = new Car(3, "Ford", "Mustang", 2019, 35000, "Red", "Used", 2, 300, 3.5, "Gasoline");
        carController.addCar(car);
        assertThat(carController.getCarsForTests()).contains(car);
    }

    @Test
    void testRemoveCar() {
        Car car = cars.get(0);
        carController.removeCar(car);
        assertThat(carController.getCarsForTests()).doesNotContain(car);
    }


    @Test
    void testEditACarPrice() {
        carController.updateCarPrice(carController.getCarById(2), 40000);
        assertThat(carController.getCarById(2).getPrice()).isEqualTo(40000);
    }
}
