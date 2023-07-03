package com.example.carsharing.repository.util;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;

public class MySQLContainerSingleton {
    private static MySQLContainer<?> container;

    private MySQLContainerSingleton() {
    }

    public static MySQLContainer<?> getContainer() {
        if (container == null) {
            container = createContainer();
            container.start();
        }
        return container;
    }

    public static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", container::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", container::getPassword);
        propertyRegistry.add("spring.datasource.username", container::getUsername);
    }

    private static MySQLContainer<?> createContainer() {
        return new MySQLContainer<>("mysql:8")
                .withDatabaseName("testing")
                .withPassword("password")
                .withUsername("username");
    }
}
