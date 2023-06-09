package com.example.carsharing.repository;

import com.example.carsharing.model.Car;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
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
    void findByBrandAndModelTest() {
        Optional<Car> actual = carRepository.findByBrandAndModel("Citroen", "C5");
        Assertions.assertEquals(1L, actual.get().getId());
        Assertions.assertEquals(Car.CarType.UNIVERSAL, actual.get().getType());
        Assertions.assertEquals(4, actual.get().getInventory());
        Assertions.assertEquals(new BigDecimal("39.90"), actual.get().getDailyFee());
        Assertions.assertEquals("C5", actual.get().getModel());
        Assertions.assertEquals("Citroen", actual.get().getBrand());

        actual = carRepository.findByBrandAndModel("Hyundai", "Tucson");
        Assertions.assertEquals(2L, actual.get().getId());
        Assertions.assertEquals(Car.CarType.SUV, actual.get().getType());
        Assertions.assertEquals(3, actual.get().getInventory());
        Assertions.assertEquals(new BigDecimal("49.90"), actual.get().getDailyFee());
        Assertions.assertEquals("Tucson", actual.get().getModel());
        Assertions.assertEquals("Hyundai", actual.get().getBrand());
    }
}
