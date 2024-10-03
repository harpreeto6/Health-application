package model;

/** 
 * A food class is a representation of food which will have Calories, Protein,
 * Fat and Carbohydrates classes as it's attributes
 * **/
public class Food {

    private String name;
    private Calories calories;
    private Protein protein;
    private Fat fat;
    private Carbohydrates carbohydrates;

    // Constructs Food without initializing any field
    public Food() {
        //stub
    }

    //EFFECT: Construct Food and set the fields to given values
    public Food(String name,Calories calories, Protein protein,Carbohydrates carbohydrates, Fat fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
    }

    //EFFECT: returns the value of Calories
    public int getCalories() {
        return this.calories.getValue();
        //stub
    }
 
    //EFFECT: returns the value of Fat
    public int getFat() {
        return this.fat.getValue();
        //stub
    }

    //EFFECT: returns the value of Carbohydrates
    public int getCarbohydates() {
        return this.carbohydrates.getValue();
        //stub
    }

    //EFFECT: returns the value of protein
    public int getProtein() {
        return this.protein.getValue();
        //stub
    }

    //EFFECT: Return name of the food
    public String getName() {
        return this.name;
        //stub
    }
    
    //MODIFIES: this
    //EFFECT: set the name to given name
    public void setName(String name) {
        this.name = name;
    }

    //MODIFIES: this
    //EFFECT: set calories to given value
    public void setCalories(int value) {
        this.calories.setValue(value);
    }

    //MODIFIES: this
    //EFFECT: set protein to given value
    public void setProtein(int value) {
        this.protein.setValue(value);
    }

    //MODIFIES: this
    //EFFECT: set carbohydrates to given value
    public void setCarbohydrates(int value) {
        this.carbohydrates.setValue(value);    
    }

    //MODIFIES: this
    //EFFECT: set fat to given value
    public void setFat(int value) {
        this.fat.setValue(value);
    }
}
