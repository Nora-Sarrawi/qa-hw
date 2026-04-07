package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import main.najah.code.Calculator;

@DisplayName("TestCalculator")
@TestMethodOrder(OrderAnnotation.class)
public class TestCalculator {

    private Calculator calculator;

    @BeforeAll
    static void beforeAll() {
        System.out.println("[TestCalculator] @BeforeAll setup");
    }

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        System.out.println("[TestCalculator] @BeforeEach new calculator");
    }

    @AfterEach
    void tearDown() {
        System.out.println("[TestCalculator] @AfterEach test done");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("[TestCalculator] @AfterAll cleanup");
    }

    static Stream<Arguments> addCases() {
        return Stream.of(
                Arguments.of(new int[] { 1, 2, 3, 4 }, 10),
                Arguments.of(new int[] {}, 0),
                Arguments.of(new int[] { -3, 2, 1 }, 0)
        );
    }

    @ParameterizedTest(name = "add({0}) = {1}")
    @MethodSource("addCases")
    @Order(1)
    @DisplayName("add valid input cases")
    void addValidParameterized(int[] input, int expected) {
        assertEquals(expected, calculator.add(input));
    }

    @Test
    @Order(2)
    @DisplayName("add timeout and multiple assertions")
    void addTimeoutAndAssertions() {
        assertTimeout(Duration.ofMillis(100), () -> calculator.add(5, 5, 5));
        assertAll(
                () -> assertEquals(15, calculator.add(5, 5, 5)),
                () -> assertEquals(-2, calculator.add(-1, -1))
        );
    }

    @Test
    @Order(3)
    @DisplayName("divide valid case")
    void divideValid() {
        assertEquals(4, calculator.divide(8, 2));
    }

    @Test
    @Order(4)
    @DisplayName("divide invalid zero divisor")
    void divideInvalid() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(9, 0));
    }

    static Stream<Arguments> factorialCases() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(1, 1),
                Arguments.of(5, 120)
        );
    }

    @ParameterizedTest(name = "factorial({0}) = {1}")
    @MethodSource("factorialCases")
    @Order(5)
    @DisplayName("factorial valid input cases")
    void factorialValidParameterized(int n, int expected) {
        assertEquals(expected, calculator.factorial(n));
    }

    @Test
    @Order(6)
    @DisplayName("factorial invalid negative")
    void factorialInvalid() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
    }

    @Test
    @Order(7)
    @Disabled("Intentionally failing test. Fix by changing expected value from 3 to 2.")
    @DisplayName("disabled intentionally failing test")
    void disabledIntentionalFailure() {
        assertEquals(3, calculator.divide(4, 2));
    }
}
