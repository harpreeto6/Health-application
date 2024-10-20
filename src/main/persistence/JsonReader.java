package persistence;


import model.DailyTracker;
import model.DailyTrackerRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads DailyTrackerRecord from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads DailyTrackerRecord from file and returns it;
    // throws IOException if an error occurs reading data from file
    public DailyTrackerRecord read() throws IOException{
        //stub
        return null;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        //stub
        return "";
    }

    // EFFECTS: parses DailyTrackerRecord from JSON object and returns it
    private DailyTrackerRecord parseWorkRoom(JSONObject jsonObject) {
        //stub
        return null;
    }

    // MODIFIES: wr
    // EFFECTS: parses DailTracker, food and other macroNutrient classes
    // from JSON object and adds them to DailyTracker
    private void addThingies(DailyTrackerRecord wr, JSONObject jsonObject) {
        
    }

    // MODIFIES: wr
    // EFFECTS: parses DailTracker, food and other macroNutrient classes from JSON object and adds it to workroom
    private void addThingy(DailyTrackerRecord wr, JSONObject jsonObject) {
        
    }
}
