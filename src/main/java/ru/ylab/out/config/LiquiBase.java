package ru.ylab.out.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class LiquiBase {

    public static void migration() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase("changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.clearCheckSums();
            liquibase.update();
            System.out.println("Migration is completed successfully");
        } catch (LiquibaseException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
