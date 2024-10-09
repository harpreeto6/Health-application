package model;

import java.util.ArrayList;
import java.util.List;

public class DailyTracker {

    private  double caloriesGoal;
    private  double proteinGoal;

    private String date;
    private List<Food> foodRecord;
    private double caloriesConsumed = 0;
    private double fatConsumed = 0;
    private double proteinConsumed = 0;
    private double carbohydratesConsumed = 0;

    //Requires: caloriesGoal and proteinGoal >= 0
    //EFFECT: Constructs a DailyTracker by setting up only Date, caloriesGoal and proteinGoal
    public DailyTracker(String date, double proteinGoal, double caloriesGoal) {
        foodRecord = new ArrayList<>();
        this.date = date;
        this.proteinGoal = proteinGoal;
        this.caloriesGoal = caloriesGoal;
    }

    //EFFECT: Constructs a  DailyTracker and set up the fields with appropriate values
    public DailyTracker(String date, Food food, double caloriesGoal, double proteinGoal) {
        foodRecord = new ArrayList<>();
        this.date = date;
        foodRecord.add(food);
        carbohydratesConsumed = food.getCarbohydates().getValue();
        proteinConsumed = food.getProtein().getValue();
        fatConsumed = food.getFat().getValue();
        caloriesConsumed = food.getCalories().getValue();
        this.caloriesGoal = caloriesGoal;
        this.proteinGoal = proteinGoal;
        
    }

    //MODIFIES: this
    //EFFECT: add new food into foodRecord List and insert other attributes of Food into appropriate fields
    public void addFood(Food food) {
        foodRecord.add(food);
        caloriesConsumed += food.getCalories().getValue();
        fatConsumed += food.getFat().getValue();
        proteinConsumed += food.getProtein().getValue();
        carbohydratesConsumed += food.getCarbohydates().getValue();
    }

    //EFFECT: Returns a List containing names of all the food items been eaten upto the point
    public List<String> getFoodHistory() {
        ArrayList<String> foodHistory = new ArrayList<>();
        for (Food item : foodRecord) {
            foodHistory.add(item.getName());
        }
        return foodHistory;
    }

    //EFFECT: Returns the amount of calories Consumed throughout day
    public double getCaloriesConsumed() {
        return this.caloriesConsumed;
    }

    //EFFECT: Returns the amount of protein Consumed throughout day
    public double getProteinConsumed() {
        return this.proteinConsumed;
    }

    //EFFECT: Returns the amount of fat Consumed throughout day
    public double getFatConsumed() {
        return this.fatConsumed;
    }
    
    //EFFECT: Returns the amount of carbohydrates Consumed throughout day
    public double getCarbohydratesConsumed() {
        return this.carbohydratesConsumed;
    }

    //EFFECT: return the date stored as String
    public String getDate() {
        return this.date;
    }

    //EFFECT: return the number of food items eaten
    public int getNumFoodItems() {
        return this.foodRecord.size();
    }

    //EFFECT: return the daily calories goal 
    public double getCaloriesGoal() {
        return this.caloriesGoal;
    }

    //EFFECT: return the daily protein goal field
    public double getProteinGoal() {
        return this.proteinGoal;
    }

    //EFFECT: return the foodRecord for today
    public List<Food> getFoodRecord() {
        return this.foodRecord;
    }

    //MODIFIES: this
    //EFFECT: remove the food item from the list of items been eaten
    public boolean removeItem(String st) {
        int i = 0;
        for (Food item : foodRecord) {
            if (item.getName() == st) {
                this.caloriesConsumed -= item.getCalories().getValue();
                this.proteinConsumed -= item.getProtein().getValue();
                this.carbohydratesConsumed -= item.getCalories().getValue();
                this.fatConsumed -= item.getFat().getValue();
                foodRecord.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

}
