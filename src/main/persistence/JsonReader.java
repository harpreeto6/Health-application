package persistence;


import model.Calories;
import model.Carbohydrates;
import model.DailyTracker;
import model.DailyTrackerRecord;
import model.EventLog;
import model.Fat;
import model.Food;
import model.Protein;
import model.Event;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads DailyTrackerRecord from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads DailyTrackerRecord from file and returns it;
    // throws IOException if an error occurs reading data from file
    public DailyTrackerRecord read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        EventLog.getInstance().clear();

        EventLog.getInstance().logEvent(new Event("Data loaded from file" ));
        return parseDailyTrackerRecord(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses DailyTrackerRecord from JSON object and returns it
    private DailyTrackerRecord parseDailyTrackerRecord(JSONObject jsonObject) {
        DailyTrackerRecord dtr = new DailyTrackerRecord();
        addTrackerRecord(dtr, jsonObject);
        return dtr;
    }

    // MODIFIES: dtr
    // EFFECTS: parses DailTracker, food and other macroNutrient classes
    // from JSON object and adds them to DailyTrackerRecord
    private void addTrackerRecord(DailyTrackerRecord dtr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("trackerRecord");
        for (Object json : jsonArray) {
            JSONObject nextTracker = (JSONObject) json;

            double caloriesGoal = nextTracker.getDouble("caloriesGoal");
            double proteinGoal = nextTracker.getDouble("proteinGoal");
            String date = nextTracker.getString("date");
            double caloriesBurned = nextTracker.getDouble("caloriesBurned");

            JSONArray jsonArrayFoodRecord = nextTracker.getJSONArray("foodRecord");
            DailyTracker tr = new DailyTracker(date, proteinGoal, caloriesGoal);
            addFoodToTracker(tr, jsonArrayFoodRecord);
            tr.addCaloriesBurned(caloriesBurned);

            dtr.addDailyTracker(tr);
        }
    }

    // Modifies: dt
    // EFFECTS: parses food items from jsonArray and add it to dt (DailyTracker)
    private void addFoodToTracker(DailyTracker dt, JSONArray jsonArray) {

        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;

            Calories calories = new Calories(nextFood.getJSONObject("calories").getDouble("value"));
            Protein protein = new Protein(nextFood.getJSONObject("protein").getDouble("value"));
            Carbohydrates carbohydrates = new Carbohydrates(nextFood.getJSONObject("carbohydrates").getDouble("value"));
            Fat fat = new Fat(nextFood.getJSONObject("fat").getDouble("value"));
            String name = nextFood.getString("name");
            Food fd = new Food(name, calories, protein, carbohydrates, fat);
            dt.addFood(fd);
        }
    }
}
