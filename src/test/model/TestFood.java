package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFood {
    
    private Food meal1;
    private Calories calories;
    private Protein protein;
    private Carbohydrates carbohydrates;
    private Fat fat;

    @BeforeEach
    void runBefore() {
        protein = new Protein(10);
        calories = new Calories(200);
        carbohydrates = new Carbohydrates(20);
        fat = new Fat(5);

        meal1 = new Food("Salad",calories,protein,carbohydrates,fat);
    }

    @Test
    void constructorTest() {
        assertEquals("Salad", meal1.getName());
        assertEquals(carbohydrates, meal1.getCarbohydates());
        assertEquals(calories, meal1.getCalories());
        assertEquals(protein, meal1.getProtein());
        assertEquals(fat, meal1.getFat());

    }

    @Test
    void setMethodsTest() {
        meal1.setCalories(1);
        meal1.setCarbohydrates(30);
        meal1.setProtein(20);
        meal1.setName("Burger");
        meal1.setFat(10);
        assertEquals(calories,meal1.getCalories());
        assertEquals(carbohydrates,meal1.getCarbohydates());
        assertEquals(protein,meal1.getProtein());
        assertEquals("Burger",meal1.getName());
        assertEquals(fat,meal1.getFat());

    }

    @Test
    void isHighInProteinTest() {
        assertFalse(meal1.isHighInProtein());
        meal1.setProtein(30);
        assertTrue(meal1.isHighInProtein());
    }

    @Test 
    void isHighInCaloriesTest() {
        assertFalse(meal1.isHighInCalories());
        meal1.setCalories(550);
        assertTrue(meal1.isHighInCalories());
    }

}
