package com.example.carsharing.security;

import com.example.carsharing.model.User;
import com.example.carsharing.model.UserRole;
import com.example.carsharing.service.UserService;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class CustomUserDetailsServiceTest {
    private static final String REGISTERED_EMAIL = "test@example.com";
    private static final String REGISTERED_PASSWORD = "password";
    private static final String UNREGISTERED_EMAIL = "random_user@example.com";
    private static final Long CUSTOMER_ROLE_ID = 1L;
    private static final Long MANAGER_ROLE_ID = 2L;
    private static final Long USER_ID = 3L;
    private UserService userService;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userDetailsService = new CustomUserDetailsService(userService);
    }

    @Test
    void loadUserByUsername() {
        UserRole userRole = new UserRole(CUSTOMER_ROLE_ID, UserRole.RoleName.CUSTOMER);
        UserRole adminRole = new UserRole(MANAGER_ROLE_ID, UserRole.RoleName.MANAGER);

        User user = new User();
        user.setId(USER_ID);
        user.setEmail(REGISTERED_EMAIL);
        user.setPassword(REGISTERED_PASSWORD);
        Set<UserRole> roles = Set.of(userRole, adminRole);
        user.setRoles(roles);

        Mockito.when(userService.findByEmail(REGISTERED_EMAIL)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(REGISTERED_EMAIL);

        Assertions.assertEquals(REGISTERED_EMAIL, userDetails.getUsername());
        Assertions.assertEquals(REGISTERED_PASSWORD, userDetails.getPassword());

        Set<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Assertions.assertEquals(2,authorities.size());
        Assertions.assertTrue(authorities.containsAll(roles.stream().
                map(r -> "ROLE_" + r.getRoleName()).collect(Collectors.toSet())));
    }
}
