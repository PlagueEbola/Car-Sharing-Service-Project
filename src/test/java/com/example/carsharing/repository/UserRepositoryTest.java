package com.example.carsharing.repository;

import com.example.carsharing.model.User;
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
class UserRepositoryTest {
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
    private UserRepository userRepository;

    @Test
    @Sql("/scripts/init_users.sql")
    void findUserByEmailTest() {
        Optional<User> actual = userRepository.findUserByEmail("alice@i.ua");
        Assertions.assertEquals(1L, actual.get().getId());
        Assertions.assertEquals("Alice", actual.get().getFirstName());
        Assertions.assertEquals("Alison", actual.get().getLastName());
        Assertions.assertEquals("alicePass", actual.get().getPassword());

        actual = userRepository.findUserByEmail("bob@i.ua");
        Assertions.assertEquals(2L, actual.get().getId());
        Assertions.assertEquals("Bob", actual.get().getFirstName());
        Assertions.assertEquals("Bobson", actual.get().getLastName());
        Assertions.assertEquals("bobPass", actual.get().getPassword());
    }
}
