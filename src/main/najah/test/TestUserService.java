package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.UserService;

@DisplayName("TestUserService")
@Execution(ExecutionMode.CONCURRENT)
public class TestUserService {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @ParameterizedTest(name = "isValidEmail(\"{0}\") should be {1}")
    @CsvSource({
            "student@test.com, true",
            "x@y.z, true",
            "studenttest.com, false",
            "student@testcom, false"
    })
    @DisplayName("isValidEmail valid and invalid input")
    void isValidEmailCases(String email, boolean expected) {
        assertTimeout(Duration.ofMillis(100), () -> userService.isValidEmail(email));
        assertEquals(expected, userService.isValidEmail(email));
    }

    @Test
    @DisplayName("isValidEmail invalid null input")
    void isValidEmailNullInvalid() {
        assertAll(
                () -> assertFalse(userService.isValidEmail(null)),
                () -> assertFalse(userService.isValidEmail("missing-dot@domain"))
        );
    }

    @ParameterizedTest(name = "authenticate({0}, {1}) should be {2}")
    @CsvSource({
            "admin, 1234, true",
            "admin, wrong, false",
            "user, 1234, false"
    })
    @DisplayName("authenticate valid and invalid input")
    void authenticateCases(String username, String password, boolean expected) {
        boolean firstAttempt = userService.authenticate(username, password);
        boolean secondAttempt = userService.authenticate(username, password);

        assertTimeout(Duration.ofMillis(100), () -> userService.authenticate(username, password));
        assertAll(
                () -> assertEquals(expected, firstAttempt),
                () -> assertEquals(firstAttempt, secondAttempt)
        );
    }
}
