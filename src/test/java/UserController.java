import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.Role;
import ru.ylab.model.User;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.UserService;

class UserControllerTest {

    @Mock
    private List<User> users;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        users = new ArrayList<>();
        User user1 = new User(1, "admin", "admin", "A", "B", Role.ADMIN, "0", "a@dmin");
        User user2 = new User(2, "manager", "manager", "B", "A", Role.MANAGER, "0", "a@dmin");
        users.add(user1);
        users.add(user2);
        userController = new UserController(new UserService(new UserRepository()));
        userController.loginUser("admin", "admin");
    }


    @Test
    void testAddUser() {
        User user = new User(3, "abc", "cde", "B", "A", Role.CLIENT, "0", "a@dmin");
        userController.registerUser(user);
        assertThat(userController.getUsersForTests()).contains(user);
    }

    @Test
    void testRemoveUser() {
        User user = users.get(0);
        userController.deleteUser(user);
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
        User user = users.get(0);
        userController.updateUserRole(user, Role.MANAGER);
        assertThat(user.getRole()).isEqualTo(Role.MANAGER);
    }
}
