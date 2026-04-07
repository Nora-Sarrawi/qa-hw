package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.najah.code.UserService;

@DisplayName("UserService Tests")
class UserServiceSimpleTest {

    private UserService userService;

	@BeforeEach
	void setUp() {
        userService = new UserService();
	}

    @Test
    @DisplayName("isValidEmail should accept typical valid email")
    void validEmail() {
        assertTrue(userService.isValidEmail("student@test.com"));
    }

    @Test
    @DisplayName("isValidEmail should reject invalid emails")
    void invalidEmail() {
        assertAll(
                () -> assertFalse(userService.isValidEmail(null)),
                () -> assertFalse(userService.isValidEmail("studenttest.com")),
                () -> assertFalse(userService.isValidEmail("student@testcom"))
        );
    }

    @Test
    @DisplayName("authenticate should return true for correct credentials")
    void authenticateValid() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @DisplayName("authenticate should return false for wrong credentials")
    void authenticateInvalid() {
        assertAll(
                () -> assertFalse(userService.authenticate("admin", "wrong")),
                () -> assertFalse(userService.authenticate("user", "1234"))
        );
    }
}
