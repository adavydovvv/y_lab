import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.ylab.dto.UserDTO;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.User;
import ru.ylab.service.UserService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() throws SQLException {
        UserDTO userDTO = new UserDTO();
        ResponseEntity<User> response = userController.registerUser(userDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService).registerUser(userDTO);
    }

    @Test
    public void testGetUsers() {
        when(userService.getUsers()).thenReturn(Collections.singletonList("User1"));

        ResponseEntity<List<String>> response = userController.getUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userService).deleteUser("username");

        ResponseEntity<Void> response = userController.deleteUser("username");

        assertEquals(204, response.getStatusCodeValue());
        verify(userService).deleteUser("username");
    }

}
