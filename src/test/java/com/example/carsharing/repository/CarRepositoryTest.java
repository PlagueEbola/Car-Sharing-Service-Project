package com.example.carsharing.repository;

import com.example.carsharing.model.Car;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {
    @Container
    static MySQLContainer<?> database = new MySQLContainer<>("mysql:8")
            .withDatabaseName("testing")
            .withPassword("password")
            .withUsername("username");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private CarRepository carRepository;

    @Test
    @Sql("/scripts/init_cars.sql")
    void findByBrandAndModelTest() {
        Optional<Car> actual = carRepository.findByBrandAndModel("Brand1", "Model1");
        Assertions.assertEquals(1L, actual.get().getId());
        Assertions.assertEquals(Car.CarType.SEDAN, actual.get().getType());
        Assertions.assertEquals(3, actual.get().getInventory());
        Assertions.assertEquals("Model1", actual.get().getModel());
        Assertions.assertEquals("Brand1", actual.get().getBrand());

        actual = carRepository.findByBrandAndModel("Brand1", "Model2");
        Assertions.assertEquals(2L, actual.get().getId());
        Assertions.assertEquals(Car.CarType.SUV, actual.get().getType());
        Assertions.assertEquals(2, actual.get().getInventory());
        Assertions.assertEquals("Model2", actual.get().getModel());
        Assertions.assertEquals("Brand1", actual.get().getBrand());
    }
}
