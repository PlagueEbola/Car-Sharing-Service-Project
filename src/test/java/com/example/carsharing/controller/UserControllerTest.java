package com.example.carsharing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import com.example.carsharing.dto.request.UserRequestDto;
import com.example.carsharing.dto.request.UserRolesRequestDto;
import com.example.carsharing.model.User;
import com.example.carsharing.model.UserRole;
import com.example.carsharing.model.UserRole.RoleName;
import com.example.carsharing.service.RoleService;
import com.example.carsharing.service.UserService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    private static final String FAKE_ENCODED_PASSWORD = "$2a$10$GPvzY8";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void getCurrentAuthenticated_Ok() {
        when(userService.findByEmail("bob@gmail.com")).thenReturn(Optional.of(createMockUser()));
        RestAssuredMockMvc.given()
                .auth().with(user("bob@gmail.com")
                        .password("123")
                        .authorities(
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"))))
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("email", Matchers.equalTo("bob@gmail.com"))
                .body("firstName", Matchers.equalTo("Bob"))
                .body("lastName", Matchers.equalTo("Bobov"))
                .body("password", Matchers.equalTo("123"))
                .body("roles[0].id", Matchers.equalTo(10))
                .body("roles[0].name", Matchers.equalTo("CUSTOMER"));
    }

    @Test
    void updateCurrentAuthenticated_Ok() {
        UserRequestDto userRequestDto = new UserRequestDto(
                "bob@gmail.com", "12345", "NewBob", "NewBobov");

        when(userService.findByEmail("bob@gmail.com")).thenReturn(Optional.of(createMockUser()));
        when(passwordEncoder.encode("12345")).thenReturn(FAKE_ENCODED_PASSWORD);
        RestAssuredMockMvc.given()
                .auth().with(user("bob@gmail.com")
                        .password("123")
                        .authorities(
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"))))
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .put("/users/me")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("email", Matchers.equalTo("bob@gmail.com"))
                .body("firstName", Matchers.equalTo("NewBob"))
                .body("lastName", Matchers.equalTo("NewBobov"))
                .body("password", Matchers.equalTo(FAKE_ENCODED_PASSWORD))
                .body("roles[0].id", Matchers.equalTo(10))
                .body("roles[0].name", Matchers.equalTo("CUSTOMER"));
        verify(userService).update(any(User.class));
    }

    @Test
    void updateRoles_Ok() {
        when(userService.findById(10L)).thenReturn(Optional.of(createMockUser()));
        when(roleService.findByRoleName("manager")).thenReturn(new UserRole(1L, RoleName.MANAGER));
        RestAssuredMockMvc.given()
                .auth().with(user("bob@gmail.com")
                        .password("123")
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER"))))
                .contentType(ContentType.JSON)
                .body(createMockUserRolesRequestDto())
                .when()
                .put("/users/{id}/role", 10L)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("email", Matchers.equalTo("bob@gmail.com"))
                .body("firstName", Matchers.equalTo("Bob"))
                .body("lastName", Matchers.equalTo("Bobov"))
                .body("password", Matchers.equalTo("123"))
                .body("roles[0].id", Matchers.equalTo(1))
                .body("roles[0].name", Matchers.equalTo("MANAGER"));
        verify(userService).update(any(User.class));
    }

    @Test
    void updateRoles_forbiddenStatus_Ok() {
        RestAssuredMockMvc.given()
                .auth().with(user("bob@gmail.com")
                        .password("123")
                        .authorities(
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"))))
                .contentType(ContentType.JSON)
                .body(createMockUserRolesRequestDto())
                .when()
                .put("/users/{id}/role", 10L)
                .then()
                .statusCode(403);
        verify(userService, never()).update(any(User.class));
    }

    private User createMockUser() {
        User mockUser = new User();
        mockUser.setId(10L);
        mockUser.setEmail("bob@gmail.com");
        mockUser.setFirstName("Bob");
        mockUser.setLastName("Bobov");
        mockUser.setPassword("123");
        mockUser.setRoles(Set.of(new UserRole(10L, RoleName.CUSTOMER)));
        return mockUser;
    }

    private UserRolesRequestDto createMockUserRolesRequestDto() {
        UserRolesRequestDto userRolesRequestDto = new UserRolesRequestDto();
        userRolesRequestDto.setRoles(Set.of("manager"));
        return userRolesRequestDto;
    }
}
