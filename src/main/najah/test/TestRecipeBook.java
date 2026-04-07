package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;

@DisplayName("TestRecipeBook")
public class TestRecipeBook {

    private RecipeBook recipeBook;

    @BeforeEach
    void setUp() {
        recipeBook = new RecipeBook();
    }

    private Recipe createRecipe(String name, String price) throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPrice(price);
        recipe.setAmtCoffee("1");
        recipe.setAmtMilk("1");
        recipe.setAmtSugar("1");
        recipe.setAmtChocolate("1");
        return recipe;
    }

    @Test
    @DisplayName("getRecipes valid initial state")
    void getRecipesValid() {
        assertTimeout(Duration.ofMillis(100), () -> recipeBook.getRecipes());
        assertAll(
                () -> assertEquals(4, recipeBook.getRecipes().length),
                () -> assertEquals(null, recipeBook.getRecipes()[0])
        );
    }

    @Test
    @DisplayName("addRecipe valid input")
    void addRecipeValid() throws RecipeException {
        assertTrue(recipeBook.addRecipe(createRecipe("Latte", "20")));
    }

    @Test
    @DisplayName("addRecipe invalid duplicate")
    void addRecipeInvalidDuplicate() throws RecipeException {
        recipeBook.addRecipe(createRecipe("Mocha", "15"));
        assertFalse(recipeBook.addRecipe(createRecipe("Mocha", "25")));
    }

    @Test
    @DisplayName("addRecipe invalid when recipe book is full")
    void addRecipeInvalidWhenFull() throws RecipeException {
        recipeBook.addRecipe(createRecipe("Americano", "10"));
        recipeBook.addRecipe(createRecipe("Espresso", "11"));
        recipeBook.addRecipe(createRecipe("Flat White", "12"));
        recipeBook.addRecipe(createRecipe("Macchiato", "13"));

        assertAll(
                () -> assertFalse(recipeBook.addRecipe(createRecipe("Mocha", "14"))),
                () -> assertEquals("Macchiato", recipeBook.getRecipes()[3].getName())
        );
    }

    @Test
    @DisplayName("deleteRecipe valid slot")
    void deleteRecipeValid() throws RecipeException {
        recipeBook.addRecipe(createRecipe("Cappuccino", "18"));
        assertEquals("Cappuccino", recipeBook.deleteRecipe(0));
    }

    @Test
    @DisplayName("deleteRecipe invalid empty slot")
    void deleteRecipeInvalidEmpty() {
        assertEquals(null, recipeBook.deleteRecipe(0));
    }

    @ParameterizedTest(name = "invalid index {0} should throw")
    @ValueSource(ints = { -1, 4, 100 })
    @DisplayName("deleteRecipe invalid index throws")
    void deleteRecipeInvalidIndex(int index) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> recipeBook.deleteRecipe(index));
    }

    @Test
    @DisplayName("editRecipe valid replaces slot and returns old name")
    void editRecipeValid() throws RecipeException {
        recipeBook.addRecipe(createRecipe("Old", "10"));
        Recipe replacement = createRecipe("New", "12");
        String oldName = recipeBook.editRecipe(0, replacement);

        assertAll(
                () -> assertEquals("Old", oldName),
                () -> assertEquals("", recipeBook.getRecipes()[0].getName())
        );
    }

    @Test
    @DisplayName("editRecipe invalid empty slot returns null")
    void editRecipeInvalidEmpty() throws RecipeException {
        assertEquals(null, recipeBook.editRecipe(0, createRecipe("Unused", "9")));
    }
}
