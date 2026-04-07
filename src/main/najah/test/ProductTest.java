package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Product;

@DisplayName("Product Tests")
public class ProductTest {
    private Product p;

	@BeforeEach
	void setUp() {
        p = new Product("Phone", 1000);
	}

	@Test
    @DisplayName("constructor should reject negative price")
	void constructorInvalidPrice() {
		assertThrows(IllegalArgumentException.class, () -> new Product("Bad", -1));
	}

    @ParameterizedTest(name = "discount {0}% should produce final price {1}")
    @CsvSource({
            "0, 1000",
            "10, 900",
            "50, 500"
    })
    @DisplayName("applyDiscount should support boundary valid values")
    void applyDiscountValidValues(double discount, double expectedPrice) {
        p.applyDiscount(discount);
        assertAll(
                () -> assertEquals(discount, p.getDiscount()),
                () -> assertEquals(expectedPrice, p.getFinalPrice(), 0.0001)
        );
    }

    @Test
    @DisplayName("applyDiscount should reject invalid values")
    void applyDiscountInvalidValues() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(-0.1)),
                () -> assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(55))
        );
    }

    @Test
    @DisplayName("getters should return original product state")
    void gettersShouldReturnState() {
        assertAll(
                () -> assertEquals("Phone", p.getName()),
                () -> assertEquals(1000, p.getPrice()),
                () -> assertEquals(0, p.getDiscount())
        );
    }
}
