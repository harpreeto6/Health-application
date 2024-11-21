package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/*
 * Class to store different Food items eaten throught the day
 * provide functionality to add and remove different items from the list
 * 
 * Not using java to get date string require for this class inOrder to test 
 * that whether all the methods are working correctly
 */
public class DailyTracker implements Writable {

    private  double caloriesGoal;
    private  double proteinGoal;

    private String date;
    private List<Food> foodRecord;
    private double caloriesConsumed = 0;
    private double fatConsumed = 0;
    private double proteinConsumed = 0;
    private double carbohydratesConsumed = 0;
    private double caloriesBurned = 0;

    

    //REQUIRES: caloriesGoal and proteinGoal >= 0
    //EFFECT: Constructs a DailyTracker by setting up only Date, caloriesGoal and proteinGoal
    public DailyTracker(String date, double proteinGoal, double caloriesGoal) {
        foodRecord = new ArrayList<>();
        this.date = date;
        this.proteinGoal = proteinGoal;
        this.caloriesGoal = caloriesGoal;
    }

    //EFFECT: Constructs a  DailyTracker and set up the fields with values provided 
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
    //EFFECT: add new food into foodRecord List and add the values of calories,
    //       protein, carbohydrates and fat from the food into appropriate fields
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

    //EFFECT: return the foodRecord
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

    //REQUIRE: value > 0
    //MODIFY: this
    //EFECT: add the value provided to the caloriesBurned field.
    public void addCaloriesBurned(double value) {
        this.caloriesBurned += value;
    }

    //EFECT: return caloriesBurned field
    public double getCaloriesBurned() {
        return this.caloriesBurned;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("caloriesGoal", caloriesGoal);
        json.put("proteinGoal", proteinGoal);
        json.put("date", date);
        json.put("caloriesBurned", caloriesBurned);
        json.put("foodRecord", foodRecordToJson());
        return json;
    }

    // EFFECTS: returns foodRecord in this DailyTracker as a JSON array
    private JSONArray foodRecordToJson() {

        JSONArray jsonArray = new JSONArray();
        for (Food f : foodRecord) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DailyTracker other = (DailyTracker) obj;
        if (other.getDate().equals(this.date)) {
            return true;
        }
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

    

}
