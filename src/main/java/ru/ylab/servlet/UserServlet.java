package ru.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.dto.UserDTO;
import ru.ylab.in.controller.UserController;
import ru.ylab.mapper.UserMapper;
import ru.ylab.model.User;
import ru.ylab.out.repository.UserRepository;
import ru.ylab.service.UserService;
import ru.ylab.utils.ValidationException;
import ru.ylab.validators.UserDTOValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private final UserController userController;
    private final ObjectMapper objectMapper;
    private final UserDTOValidator userDTOValidator;

    public UserServlet() {
        this.userController = new UserController(new UserService(new UserRepository()));
        this.objectMapper = new ObjectMapper();
        this.userDTOValidator = new UserDTOValidator();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        UserDTO userDTO = objectMapper.readValue(request.getReader(), UserDTO.class);

        try {
            if (!userDTOValidator.validate(userDTO)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
            userController.registerUser(user);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, UserMapper.INSTANCE.userToUserDTO(user));
        } catch (ValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("Error registering user: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String pathInfo = request.getPathInfo();
        List<String> users;

        if (pathInfo == null || pathInfo.equals("/")) {
            String sortBy = request.getParameter("sortBy");
            String filterBy = request.getParameter("filterBy");
            String filterValue = request.getParameter("filterValue");

            try {
                if (sortBy != null) {
                    users = handleSortRequest(sortBy);
                } else if (filterBy != null && filterValue != null) {
                    users = handleFilterRequest(filterBy, filterValue);
                } else {
                    users = userController.getUsers();
                }

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(response.getWriter(), users);
                return;

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
                return;
            }
        }

        String username = extractUsername(pathInfo);
        if (username != null) {
            try {
                User user = userController.getUserByUsername(username);
                response.setContentType("application/json");
                objectMapper.writeValue(response.getWriter(), UserMapper.INSTANCE.userToUserDTO(user));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("User not found: " + e.getMessage());
            }
        }
    }


    private List<String> handleSortRequest(String sortBy) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        switch (sortBy) {
            case "firstName":
                return userController.getUsersSortedByFirstName();
            case "lastName":
                return userController.getUsersSortedByLastName();
            case "email":
                return userController.getUsersSortedByEmail();
            case "purchasesCount":
                return userController.getUsersSortedByPurchasesCount();
            default:
                throw new IllegalArgumentException("Invalid sortBy parameter");
        }
    }

    private List<String> handleFilterRequest(String filterBy, String filterValue) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        switch (filterBy) {
            case "firstName":
                return userController.filterUsersByFirstName(filterValue);
            case "lastName":
                return userController.filterUsersByLastName(filterValue);
            case "email":
                return userController.filterUsersByEmail(filterValue);
            case "purchasesCount":
                try {
                    int count = Integer.parseInt(filterValue);
                    return userController.filterUsersByPurchasesCount(count);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid purchasesCount value");
                }
            default:
                throw new IllegalArgumentException("Invalid filterBy parameter");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String username = extractUsername(request.getPathInfo());
        User existingUser = userController.getUserByUsername(username);
        if (existingUser == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UserDTO userDTO = objectMapper.readValue(request.getReader(), UserDTO.class);
        try {
            if (!userDTOValidator.validate(userDTO)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            userController.updateUserPassword(existingUser, userDTO.getPassword());
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), UserMapper.INSTANCE.userToUserDTO(existingUser));
        } catch (ValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String username = extractUsername(request.getPathInfo());
        User existingUser = userController.getUserByUsername(username);
        if (existingUser == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        userController.deleteUser(username);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private String extractUsername(String pathInfo) {
        return (pathInfo != null && pathInfo.split("/").length > 1) ? pathInfo.split("/")[1] : null;
    }
}
