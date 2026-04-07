package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Product;

@DisplayName("TestProduct")
public class TestProduct {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Phone", 1000.0);
    }

    @Test
    @DisplayName("constructor invalid input should throw")
    void constructorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Bad", -1.0));
    }

    @ParameterizedTest(name = "discount {0}% -> final {1}")
    @CsvSource({
            "0, 1000",
            "10, 900",
            "50, 500"
    })
    @DisplayName("applyDiscount and getFinalPrice valid parameterized")
    void applyDiscountAndFinalPriceValid(double discount, double expectedFinal) {
        assertTimeout(Duration.ofMillis(100), () -> product.applyDiscount(discount));
        assertAll(
                () -> assertEquals(discount, product.getDiscount()),
                () -> assertEquals(expectedFinal, product.getFinalPrice(), 0.0001)
        );
    }

    @ParameterizedTest(name = "invalid discount {0} should throw")
    @CsvSource({ "-0.1", "50.1", "80" })
    @DisplayName("applyDiscount invalid values")
    void applyDiscountInvalid(double invalidDiscount) {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(invalidDiscount));
    }

    @Test
    @DisplayName("getName getPrice getDiscount valid values")
    void gettersValid() {
        assertAll(
                () -> assertEquals("Phone", product.getName()),
                () -> assertEquals(1000.0, product.getPrice(), 0.0001),
                () -> assertEquals(0.0, product.getDiscount(), 0.0001)
        );
    }
}
