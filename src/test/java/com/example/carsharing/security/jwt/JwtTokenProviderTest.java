package com.example.carsharing.security.jwt;

import com.example.carsharing.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

class JwtTokenProviderTest {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password";;
    private static final String SECRET_KEY = "secret";
    private static final long VALIDITY_IN_MILLISECONDS = 3600000;
    private static final long MAX_TOKEN_VALIDITY_DEVIATION_IN_MILLISECONDS = 10000;
    private UserDetailsService userDetailsService;
    private JwtTokenProvider jwtTokenProvider;
    private String token;
    private List<String> roles;

    @BeforeEach
    void setUp() {
        userDetailsService = Mockito.mock(CustomUserDetailsService.class);
        jwtTokenProvider = new JwtTokenProvider(userDetailsService);

        jwtTokenProvider.setSecretKey(SECRET_KEY);
        jwtTokenProvider.setValidityInMilliseconds(VALIDITY_IN_MILLISECONDS);

        roles = List.of("USER", "ADMIN");
        token = jwtTokenProvider.createToken(EMAIL, roles);
        Assertions.assertNotNull(token);
    }

    @Test
    void createToken() {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        Assertions.assertEquals(EMAIL, claims.getSubject());
        Assertions.assertEquals(roles, claims.get("roles", List.class));

        long difference = claims.getExpiration().getTime() - new Date().getTime() - VALIDITY_IN_MILLISECONDS;
        Assertions.assertTrue(Math.abs(difference) < MAX_TOKEN_VALIDITY_DEVIATION_IN_MILLISECONDS);
    }

    @Test
    void getAuthentication() {
        User.UserBuilder builder;
        builder = User.withUsername(EMAIL);
        builder.password(PASSWORD);
        builder.roles(roles.toArray(String[]::new));
        Mockito.when(userDetailsService.loadUserByUsername(EMAIL)).thenReturn(builder.build());

        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        Assertions.assertNotNull(authentication);
        Assertions.assertEquals(UsernamePasswordAuthenticationToken.class, authentication.getClass());
        Assertions.assertEquals(EMAIL, authentication.getName());

        List<String> expectedAuthorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.substring(5))
                .toList();
        Assertions.assertTrue(expectedAuthorities.containsAll(roles));
        Assertions.assertTrue(authentication.isAuthenticated());
    }

    @Test
    void getUsername() {
        String actualUsername = jwtTokenProvider.getUsername(token);
        Assertions.assertEquals(EMAIL, actualUsername);
    }

    @Test
    void resolveToken() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");

        String actual = jwtTokenProvider.resolveToken(request);
        Assertions.assertEquals("valid-token", actual);

        Mockito.when(request.getHeader("Authorization")).thenReturn("Token without \"Bearer \"");
        actual = jwtTokenProvider.resolveToken(request);
        Assertions.assertNull(actual);
    }

    @Test
    void validateToken_() {
        Assertions.assertTrue(jwtTokenProvider.validateToken(token));
    }
}
