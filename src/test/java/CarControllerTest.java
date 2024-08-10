import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ylab.in.controller.CarController;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.Car;
import ru.ylab.out.repository.CarRepository;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.CarService;
import ru.ylab.service.UserService;

@Testcontainers
class CarControllerTest extends AbstractIntegrationTest {
    @InjectMocks
    private CarController carController;
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carController = new CarController(new CarService(new CarRepository()));
        userController = new UserController(new UserService(new UserRepository()));
        userController.loginUser("manager", "manager");

        carController.addCar(new Car(1, "Toyota", "Camry", 2021, 30000, "White", "New", 1, 250, 2.5, "Gasoline"));
        carController.addCar(new Car(2, "Honda", "Accord", 2020, 28000, "Black", "New", 1, 240, 2.0, "Gasoline"));
    }

    @Test
    void testAddCar() {
        Car car = new Car(3, "Ford", "Mustang", 2019, 35000, "Red", "Used", 2, 300, 3.5, "Gasoline");
        carController.addCar(car);
        assertThat(carController.getCarsForTests()).contains(car);
    }

    @Test
    void testRemoveCar() {
        Car car = carController.getCarById(1);
        carController.removeCar(car);
        assertThat(carController.getCarsForTests()).doesNotContain(car);
    }

    @Test
    void testEditACarPrice() {
        carController.updateCarPrice(carController.getCarById(2), 40000);
        assertThat(carController.getCarById(2).getPrice()).isEqualTo(40000);
    }
}
