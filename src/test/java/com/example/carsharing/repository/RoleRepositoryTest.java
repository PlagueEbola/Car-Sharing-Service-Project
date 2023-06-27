package com.example.carsharing.repository;

import com.example.carsharing.model.UserRole;
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
class RoleRepositoryTest {
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
    private RoleRepository roleRepository;

    @Test
    void findByRoleNameTest() {
        Optional<UserRole> actual = roleRepository.findByRoleName(UserRole.RoleName.CUSTOMER);
        Assertions.assertEquals(1L, actual.get().getId());
        Assertions.assertEquals("CUSTOMER", actual.get().getRoleName().name());
        actual = roleRepository.findByRoleName(UserRole.RoleName.MANAGER);
        Assertions.assertEquals(2L, actual.get().getId());
        Assertions.assertEquals("MANAGER", actual.get().getRoleName().name());
    }
}
