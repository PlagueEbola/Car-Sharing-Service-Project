package com.example.carsharing.repository;

import com.example.carsharing.model.Rental;
import java.time.LocalDate;
import java.util.List;
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
class RentalRepositoryTest {
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
    private RentalRepository rentalRepository;

    @Test
    @Sql("/scripts/init_rentals.sql")
    void findAllByUserIdAndActualReturnDateIsNullTest() {
        List<Rental> actual = rentalRepository.findAllByUserIdAndActualReturnDateIsNull(1L);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(1L, actual.get(0).getId());
        Assertions.assertEquals(2L, actual.get(1).getId());

        actual = rentalRepository.findAllByUserIdAndActualReturnDateIsNull(2L);
        Assertions.assertEquals(actual.size(), 0);
    }

    @Test
    @Sql("/scripts/init_rentals.sql")
    void findAllByUserIdAndActualReturnDateIsNotNullTest() {
        List<Rental> actual = rentalRepository.findAllByUserIdAndActualReturnDateIsNotNull(1L);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(3L, actual.get(0).getId());
        Assertions.assertEquals(LocalDate.of(2023, 6, 1), actual.get(0).getActualReturnDate());

        actual = rentalRepository.findAllByUserIdAndActualReturnDateIsNotNull(2L);
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(4L, actual.get(0).getId());
        Assertions.assertEquals(5L, actual.get(1).getId());
        Assertions.assertEquals(6L, actual.get(2).getId());
        Assertions.assertEquals(LocalDate.of(2023, 6, 1), actual.get(0).getActualReturnDate());
        Assertions.assertEquals(LocalDate.of(2023, 6, 2), actual.get(1).getActualReturnDate());
        Assertions.assertEquals(LocalDate.of(2023, 6, 3), actual.get(2).getActualReturnDate());
    }
}
