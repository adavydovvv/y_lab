import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractIntegrationTest {

    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("carshop")
            .withUsername("testuser")
            .withPassword("testpass");

    @BeforeAll
    public static void startContainer() {
        postgresContainer.start();
        System.setProperty("jdbc:postgresql://localhost:5438/carshop", postgresContainer.getJdbcUrl());
        System.setProperty("admin", postgresContainer.getUsername());
        System.setProperty("ylab", postgresContainer.getPassword());
    }

    @AfterAll
    public static void stopContainer() {
        postgresContainer.stop();
    }
}
