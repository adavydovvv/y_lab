

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
import ru.ylab.dto.UserDTO;
import ru.ylab.in.controller.UserController;
import ru.ylab.model.Role;
import ru.ylab.model.User;
import ru.ylab.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CarShopApplication.class) // Укажите ваш основной класс приложения
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void deleteUser() throws Exception {
        String username = "testUser";

        mockMvc.perform(delete("/api/users/{username}", username))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(username);
    }

    @Test
    void updateUserPurchasesCount() throws Exception {
        String username = "testUser";
        User user = new User();

        mockMvc.perform(put("/api/users/{username}/purchases", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).updateUserPurchasesCount(user);
    }

    @Test
    void registerUser() throws Exception {
        UserDTO userDTO = new UserDTO();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).registerUser(userDTO);
    }

    @Test
    void loginUser() throws Exception {
        String username = "testUser";
        String password = "password";

        mockMvc.perform(post("/api/users/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk());

        verify(userService, times(1)).loginUser(username, password);
    }

    @Test
    void updateUserPassword() throws Exception {
        String username = "testUser";
        String newPassword = "newPassword";

        mockMvc.perform(put("/api/users/{username}/password", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).updateUserPassword(any(User.class), eq(newPassword));
    }

    @Test
    void updateUserRole() throws Exception {
        String username = "testUser";
        Role role = Role.CLIENT;

        mockMvc.perform(put("/api/users/{username}/role", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).updateUserRole(username, role);
    }

    @Test
    void getUsers() throws Exception {
        List<String> users = Collections.singletonList("testUser");
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("testUser"));
    }

    @Test
    void getUsersSorted() throws Exception {
        String sortBy = "username";
        List<UserDTO> sortedUsers = Collections.singletonList(new UserDTO());
        when(userService.getUsersSorted(sortBy)).thenReturn(sortedUsers);

        mockMvc.perform(get("/api/users/sorted")
                        .param("sortBy", sortBy))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getUsersFiltered() throws Exception {
        String filterBy = "role";
        String filterValue = "admin";
        List<UserDTO> filteredUsers = Collections.singletonList(new UserDTO());
        when(userService.getUsersFiltered(filterBy, filterValue)).thenReturn(filteredUsers);

        mockMvc.perform(get("/api/users/filtered")
                        .param("filterBy", filterBy)
                        .param("filterValue", filterValue))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getLastUserId() throws Exception {
        int lastUserId = 42;
        when(userService.getLastUserId()).thenReturn(lastUserId);

        mockMvc.perform(get("/api/users/last-id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(lastUserId));

        verify(userService, times(1)).getLastUserId();
    }

    @Test
    void getUserByUsername() throws Exception {
        String username = "testUser";
        User user = new User();
        when(userService.getUserByUsername(username)).thenReturn(user);

        mockMvc.perform(get("/api/users/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, times(1)).getUserByUsername(username);
    }
}
