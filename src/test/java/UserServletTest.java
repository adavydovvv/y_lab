import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.UserDTO;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.Role;
import ru.ylab.servlet.UserServlet;
import ru.ylab.validators.UserDTOValidator;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServletTest {

    private UserServlet userServlet;
    private UserController userController;
    private UserDTOValidator userDTOValidator;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        userController = mock(UserController.class);
        userDTOValidator = mock(UserDTOValidator.class);
        objectMapper = new ObjectMapper();
        userServlet = new UserServlet();
    }

    @Test
    public void testDoPost_Success() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("johndoe");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setPassword("securePassword123");
        userDTO.setPhone("+77777777777");
        userDTO.setRole(Role.CLIENT);


        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(userDTO))));
        when(userDTOValidator.validate(any())).thenReturn(true);

        doNothing().when(userController).registerUser(any());

        userServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write(anyString());
    }

    @Test
    public void testDoPost_ValidationError() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        UserDTO userDTO = new UserDTO();

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(objectMapper.writeValueAsString(userDTO))));
        when(userDTOValidator.validate(any())).thenReturn(false);

        userServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer, never()).write(anyString());
    }

}
