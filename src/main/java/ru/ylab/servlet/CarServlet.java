package ru.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.dto.CarDTO;
import ru.ylab.in.controller.CarController;
import ru.ylab.mapper.CarMapper;
import ru.ylab.model.Car;
import ru.ylab.out.repository.CarRepository;
import ru.ylab.service.CarService;
import ru.ylab.utils.ValidationException;
import ru.ylab.validators.CarDTOValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cars/*")
public class CarServlet extends HttpServlet {

    private final CarController carController;
    private final ObjectMapper objectMapper;
    private final CarDTOValidator carDTOValidator;

    public CarServlet() {
        this.carController = new CarController(new CarService(new CarRepository()));
        this.objectMapper = new ObjectMapper();
        this.carDTOValidator = new CarDTOValidator();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String pathInfo = request.getPathInfo();
        List<String> cars;

        if (pathInfo == null || pathInfo.equals("/")) {
            String filterBy = request.getParameter("filterBy");
            String filterValue = request.getParameter("filterValue");

            try {
                if (filterBy != null && filterValue != null) {
                    cars = handleFilterRequest(filterBy, filterValue);
                } else {
                    cars = carController.getCars();
                }

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(response.getWriter(), cars);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
            }
        } else {
            String carIdStr = extractCarId(pathInfo);
            if (carIdStr != null) {
                try {
                    Car car = carController.getCarById(Integer.parseInt(carIdStr));
                    response.setContentType("application/json");
                    objectMapper.writeValue(response.getWriter(), CarMapper.INSTANCE.carToCarDTO(car));
                    return;
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Car not found: " + e.getMessage());
                    return;
                }
            }

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        }
    }


    private List<String> handleFilterRequest(String filterBy, String filterValue) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        switch (filterBy) {
            case "brand":
                return carController.filterCarsByBrand(filterValue);
            case "model":
                return carController.filterCarsByModel(filterValue);
            case "price":
                try {
                    int price = Integer.parseInt(filterValue);
                    return carController.filterCarsByPrice(price);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid price value");
                }
            case "condition":
                return carController.filterCarsByCondition(filterValue);
            case "engineType":
                return carController.filterCarsByEngineType(filterValue);
            default:
                throw new IllegalArgumentException("Invalid filterBy parameter");
        }
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        CarDTO carDTO = objectMapper.readValue(request.getReader(), CarDTO.class);

        try {
            if (!carDTOValidator.validate(carDTO)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Car car = CarMapper.INSTANCE.carDTOToCar(carDTO);
            carController.addCar(car);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, CarMapper.INSTANCE.carToCarDTO(car));
        } catch (ValidationException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("Error adding car: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String carIdStr = extractCarId(request.getPathInfo());
        if (carIdStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        CarDTO carDTO = objectMapper.readValue(request.getReader(), CarDTO.class);
        try {
            carController.updateCarPrice(carController.getCarById(Integer.parseInt(carIdStr)), carDTO.getPrice());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Car not found: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String carIdStr = extractCarId(request.getPathInfo());
        if (carIdStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            carController.removeCar(carController.getCarById(Integer.parseInt(carIdStr)));
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Car not found: " + e.getMessage());
        }
    }

    private String extractCarId(String pathInfo) {
        return (pathInfo != null && pathInfo.split("/").length > 1) ? pathInfo.split("/")[1] : null;
    }
}
