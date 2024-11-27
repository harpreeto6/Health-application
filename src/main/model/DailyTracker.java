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

        EventLog.getInstance().logEvent(new Event("DailyTracker Constructed with " + date + " date constructed"));
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

        EventLog.getInstance().logEvent(new Event(food.getName() + " saved in DailyTracker"));
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

        // EventLog.getInstance().logEvent(new Event("Food data inside DailyTracker accessed"));
        return this.foodRecord;
    }

    //MODIFIES: this
    //EFFECT: remove the food item from the list of items been eaten
    public boolean removeItem(String st) {
        // int i = 0;
        
        for (Food item : foodRecord) {
            if (item.getName().equals(st)) {
                this.caloriesConsumed -= item.getCalories().getValue();
                this.proteinConsumed -= item.getProtein().getValue();
                this.carbohydratesConsumed -= item.getCarbohydates().getValue();
                this.fatConsumed -= item.getFat().getValue();
                foodRecord.remove(item);
                EventLog.getInstance().logEvent(new Event("Removed " + st + " from DailyTracker"));
                return true;
            }
            // i++;
        }
        return false;
    }

    //REQUIRE: value > 0
    //MODIFY: this
    //EFECT: add the value provided to the caloriesBurned field.
    public void addCaloriesBurned(double value) {
        this.caloriesBurned += value;
        EventLog.getInstance().logEvent(new Event("Added " + value + " calories burned"));
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

}
