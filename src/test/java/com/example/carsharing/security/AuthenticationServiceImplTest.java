package com.example.carsharing.security;

import com.example.carsharing.exception.AuthenticationException;
import com.example.carsharing.model.User;
import com.example.carsharing.model.UserRole;
import com.example.carsharing.service.RoleService;
import com.example.carsharing.service.UserService;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthenticationServiceImplTest {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "Lee";
    private static final String LAST_NAME = "Smith";
    private static final String USER_ROLE = "CUSTOMER";
    private static final Long ROLE_ID = 1L;
    private static final Long USER_ID = 1L;
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        roleService = Mockito.mock(RoleService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        authenticationService = new AuthenticationServiceImpl(userService, roleService, passwordEncoder);
    }

    @Test
    void register() {
        UserRole role = new UserRole();
        role.setId(ROLE_ID);
        role.setRoleName(UserRole.RoleName.CUSTOMER);

        User user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRoles(Set.of(role));

        Mockito.when(roleService.findByRoleName(USER_ROLE)).thenReturn(role);
        Mockito.when(userService.add(Mockito.any(User.class))).thenReturn(user);

        User actual = authenticationService.register(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(EMAIL, actual.getEmail());
        Assertions.assertEquals(PASSWORD, actual.getPassword());
        Assertions.assertEquals(Set.of(role), actual.getRoles());

        Mockito.verify(roleService).findByRoleName(USER_ROLE);
        Mockito.verify(userService).add(Mockito.any(User.class));
    }

    @Test
    void loginWithValidCredentials() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        Mockito.when(userService.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(PASSWORD, user.getPassword())).thenReturn(true);

        User actual = null;
        try {
            actual = authenticationService.login(EMAIL, PASSWORD);
        } catch (AuthenticationException e) {
            Assertions.fail(e.getMessage());
        }

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(USER_ID, actual.getId());
        Assertions.assertEquals(EMAIL, actual.getEmail());
        Assertions.assertEquals(PASSWORD, actual.getPassword());

        Mockito.verify(userService).findByEmail(EMAIL);
        Mockito.verify(passwordEncoder).matches(PASSWORD, user.getPassword());
    }

    @Test
    void loginWithInvalidCredentials() {
        Mockito.when(userService.findByEmail(EMAIL)).thenReturn(Optional.empty());

        Assertions.assertThrows(AuthenticationException.class, () ->
                authenticationService.login(EMAIL, PASSWORD));

        Mockito.verify(userService).findByEmail(EMAIL);
        Mockito.verifyNoMoreInteractions(passwordEncoder);

    }
}
