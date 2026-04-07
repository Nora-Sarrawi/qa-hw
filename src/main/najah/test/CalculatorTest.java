package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import main.najah.code.Calculator;

@DisplayName("Calculator Tests")
@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public class CalculatorTest {
	
    private Calculator calc;

    @BeforeAll
    static void beforeAll() {
        System.out.println("CalculatorTest setup complete");
    }

	@BeforeEach
	void setUp() {
        calc = new Calculator();
	}

    @AfterEach
    void afterEach() {
        System.out.println("CalculatorTest method done");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("CalculatorTest finished");
    }

    @Test
    @Order(1)
    @DisplayName("add should return the sum for many values")
    void addValidNumbers() {
        assertAll(
                () -> assertEquals(10, calc.add(1, 2, 3, 4)),
                () -> assertEquals(0, calc.add()),
                () -> assertEquals(-2, calc.add(-1, -1))
        );
    }

    @Test
    @Order(2)
    @DisplayName("divide should return quotient for valid input")
    void divideValidNumbers() {
        assertEquals(5, calc.divide(10, 2));
    }

    @Test
    @Order(3)
    @DisplayName("divide should throw for zero divisor")
    void divideByZeroInvalid() {
        assertThrows(ArithmeticException.class, () -> calc.divide(12, 0));
    }

    @Test
    @Order(4)
    @DisplayName("factorial should return expected values")
    void factorialValidValues() {
        assertAll(
                () -> assertEquals(1, calc.factorial(0)),
                () -> assertEquals(120, calc.factorial(5))
        );
    }

    @Test
    @Order(5)
    @DisplayName("factorial should throw for negative input")
    void factorialInvalidNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1));
    }

    @Test
    @Order(6)
    @DisplayName("add should complete quickly")
    void addTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> calc.add(1, 2, 3));
    }

    @Test
    @Disabled("Intentionally failing test per assignment. Fix by changing expected result to 2.")
    @DisplayName("intentionally failing disabled example")
    void disabledFailingTest() {
        assertEquals(3, calc.divide(4, 2));
    }
}
