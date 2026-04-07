package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;

@DisplayName("RecipeBook Tests")
class RecipeBookTest {

    private RecipeBook recipeBook;

    @BeforeEach
    void setUp() {
        recipeBook = new RecipeBook();
    }

    private Recipe buildRecipe(String name, String price) throws RecipeException {
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
    @DisplayName("addRecipe should add valid recipe")
    void addRecipeValid() throws RecipeException {
        Recipe recipe = buildRecipe("Latte", "20");
        assertTrue(recipeBook.addRecipe(recipe));
    }

    @Test
    @DisplayName("addRecipe should reject duplicate recipe")
    void addRecipeDuplicateInvalid() throws RecipeException {
        Recipe recipe = buildRecipe("Mocha", "15");
        recipeBook.addRecipe(recipe);
        Recipe duplicate = buildRecipe("Mocha", "25");
        assertFalse(recipeBook.addRecipe(duplicate));
    }

    @Test
    @DisplayName("deleteRecipe should return recipe name for existing slot")
    void deleteRecipeValid() throws RecipeException {
        recipeBook.addRecipe(buildRecipe("Cappuccino", "18"));
        assertEquals("Cappuccino", recipeBook.deleteRecipe(0));
    }

    @Test
    @DisplayName("deleteRecipe should return null for empty slot")
    void deleteRecipeInvalid() {
        assertNull(recipeBook.deleteRecipe(0));
    }

    @Test
    @DisplayName("editRecipe should return old name and update slot")
    void editRecipeValid() throws RecipeException {
        recipeBook.addRecipe(buildRecipe("Old", "10"));
        Recipe replacement = buildRecipe("New", "12");
        String oldName = recipeBook.editRecipe(0, replacement);

        assertAll(
                () -> assertEquals("Old", oldName),
                () -> assertEquals("", recipeBook.getRecipes()[0].getName())
        );
    }

    @Test
    @DisplayName("editRecipe should return null for empty slot")
    void editRecipeInvalid() throws RecipeException {
        assertNull(recipeBook.editRecipe(0, buildRecipe("Unused", "9")));
    }
}
