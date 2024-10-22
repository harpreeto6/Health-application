package model;

import org.json.JSONObject;

import persistence.Writable;

/** 
 * A food class is a representation of food item which will have Calories, Protein,
 * Fat and Carbohydrates classes as it's attributes
 * Class will tell  whether a food utem is high in Calories and/or high in Protein
 * **/
public class Food implements Writable {

    private String name;
    private Calories calories;
    private Protein protein;
    private Fat fat;
    private Carbohydrates carbohydrates;

    private final double highProtein = 20;
    private final double highCalories = 500;

    //EFFECT: Construct Food and set the fields to given values
    public Food(String name,Calories calories, Protein protein,Carbohydrates carbohydrates, Fat fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
    }

    //EFFECT: returns the value of Calories
    public Calories getCalories() {
        return this.calories;
    }
 
    //EFFECT: returns the value of Fat
    public Fat getFat() {
        return this.fat;
    }

    //EFFECT: returns the value of Carbohydrates
    public Carbohydrates getCarbohydates() {
        return this.carbohydrates;
    }

    //EFFECT: returns the value of protein
    public Protein getProtein() {
        return this.protein;
    }

    //EFFECT: Return name of the food
    public String getName() {
        return this.name;
    }
    
    //MODIFIES: this
    //EFFECT: set the name to given name
    public void setName(String name) {
        this.name = name;
    }

    //REQUIRE: value >= 0
    //MODIFIES: this
    //EFFECT: set calories to given value
    public void setCalories(double value) {
        this.calories.setValue(value);
    }

    //REQUIRE: value >= 0
    //MODIFIES: this
    //EFFECT: set protein to given value
    public void setProtein(double value) {
        this.protein.setValue(value);
    }

    //REQUIRE: value >= 0
    //MODIFIES: this
    //EFFECT: set carbohydrates to given value
    public void setCarbohydrates(double value) {
        this.carbohydrates.setValue(value);    
    }
    
    //REQUIRE: value >= 0
    //MODIFIES: this
    //EFFECT: set fat to given value
    public void setFat(double value) {
        this.fat.setValue(value);
    }

    //EFFECT: return true if protein.getValue() >= highProtein
    public  boolean isHighInProtein() {
        if (protein.getValue() >= highProtein) {
            return true;
        } else {
            return false;
        }
    }

    //EFFECT: return true if calories.getValue() >= highCalories
    public boolean isHighInCalories() {
        if (calories.getValue() >= highCalories) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("calories", calories.toJson());
        json.put("protein", protein.toJson());
        json.put("carbohydrates", carbohydrates.toJson());
        json.put("fat", fat.toJson());
        return json;
    }
}
