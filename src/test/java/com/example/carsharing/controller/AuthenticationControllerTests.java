package com.example.carsharing.controller;

import com.example.carsharing.dto.request.UserLoginRequestDto;
import com.example.carsharing.dto.request.UserRequestDto;
import com.example.carsharing.exception.AuthenticationException;
import com.example.carsharing.model.User;
import com.example.carsharing.model.UserRole;
import com.example.carsharing.security.AuthenticationService;
import com.example.carsharing.security.jwt.JwtTokenProvider;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTests {
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void loginWithValidCredentials() {
        String email = "bob@io.com";
        String password = "123";
        User bob = new User(1L, email, password);
        UserRole customerRole = new UserRole();
        customerRole.setRoleName(UserRole.RoleName.CUSTOMER);
        bob.setRoles(Set.of(customerRole));

        try {
            Mockito.when(authenticationService.login(email, password)).thenReturn(bob);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        String token = "token";
        Mockito.when(jwtTokenProvider.createToken(email, List.of(customerRole.getRoleName().name()))).thenReturn(token);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new UserLoginRequestDto(email, password))
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("email", Matchers.equalTo(email))
                .body("token", Matchers.equalTo(token))
                .extract()
                .response();
    }

    @Test
    public void loginWithInvalidCredentials_ThrowsAuthenticationException() {
        String email = "invalidUser";
        String password = "invalidPassword";

        try {
            Mockito.when(authenticationService.login(email, password)).thenThrow(AuthenticationException.class);
            RestAssuredMockMvc.given()
                    .contentType(ContentType.JSON)
                    .body(new UserLoginRequestDto(email, password))
                    .when()
                    .post("/login");
        } catch (Exception e) {
            Assertions.assertEquals("Request processing failed: "
                    + "com.example.carsharing.exception.AuthenticationException", e.getMessage());
            return;
        }
        Assertions.fail();
    }

    @Test
    public void register() {
        String email = "test@example.com";
        String password = "password";
        String firstName = "Lee";
        String lastName = "Smith";

        Mockito.when(authenticationService.register(email, password, firstName, lastName))
                .thenReturn(new User(5L, email, firstName, lastName, password,
                        Set.of(new UserRole(1L, UserRole.RoleName.CUSTOMER))));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new UserRequestDto(email, password,firstName,lastName))
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("email", Matchers.equalTo(email))
                .body("firstName", Matchers.equalTo(firstName))
                .body("lastName", Matchers.equalTo(lastName))
                .body("password", Matchers.equalTo(password));
    }
}
