package com.example.carsharing.repository;

import com.example.carsharing.model.User;
import com.example.carsharing.model.UserRole;
import com.example.carsharing.repository.util.MySQLContainerSingleton;
import java.util.List;
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
    static MySQLContainer<?> database = MySQLContainerSingleton.getContainer();

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        MySQLContainerSingleton.setDatasourceProperties(propertyRegistry);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("/scripts/init_users_with_roles.sql")
    void findUserByEmailTest() {
        Optional<User> actual = userRepository.findUserByEmail("bob@gmail.com");
        Assertions.assertEquals(1L, actual.get().getId());
        Assertions.assertEquals("Bob", actual.get().getFirstName());
        Assertions.assertEquals("Bobov", actual.get().getLastName());
        Assertions.assertEquals("$2a$10$GPvzY8rZ9tpWEC223ZZqHO1xL.gXoa865ICIFLItladaswRbwJFz.",
                actual.get().getPassword());
        Assertions.assertEquals(List.of(1L), actual.get().getRoles()
                .stream()
                .map(UserRole::getId).toList());

        actual = userRepository.findUserByEmail("alice@gmail.com");
        Assertions.assertEquals(2L, actual.get().getId());
        Assertions.assertEquals("Alice", actual.get().getFirstName());
        Assertions.assertEquals("Aliceva", actual.get().getLastName());
        Assertions.assertEquals("$2a$10$BAq6EA07KIgKPkC1ftgWheGUvteB3SqseWkjtkrToSwDXh/RyQrTK",
                actual.get().getPassword());
        Assertions.assertEquals(List.of(2L), actual.get().getRoles()
                .stream()
                .map(UserRole::getId).toList());
    }
}
