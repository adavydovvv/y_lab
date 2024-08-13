import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.Role;
import ru.ylab.model.User;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.UserService;

@Testcontainers
public class UserControllerTest extends AbstractIntegrationTest {

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(new UserService(new UserRepository()));
        userController.loginUser("admin", "admin");


        userController.registerUser(new User(1, "admin", "admin", "A", "B", Role.ADMIN, "0", "a@dmin"));
        userController.registerUser(new User(2, "manager", "manager", "B", "A", Role.MANAGER, "0", "a@dmin"));
    }

    @Test
    void testAddUser() throws SQLException {
        User user = new User(3, "abc", "cde", "B", "A", Role.CLIENT, "0", "a@dmin");
        userController.registerUser(user);
        assertThat(userController.getUsersForTests()).contains(user);
    }

    @Test
    void testRemoveUser() {
        User user = userController.getUserByUsername("admin");
        userController.deleteUser(user.getUsername());
        assertThat(userController.getUsersForTests()).doesNotContain(user);
    }
    @Test
    void testGetUserByUsername() {
        User result = userController.getUserByUsername("admin");
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("admin");
    }

    @Test
    void testUpdateUserRole() {
        User user = userController.getUserByUsername("admin");
        userController.updateUserRole(user.getUsername(), Role.MANAGER);
        assertThat(user.getRole()).isEqualTo(Role.MANAGER);
    }
}
