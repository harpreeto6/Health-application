package persistence;

import model.Food;
import model.DailyTracker;
import model.DailyTrackerRecord;

import model.Protein;
import model.Calories;
import model.Carbohydrates;
import model.Fat;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {
    
    @Test
    void testWriterInvalidFile() {
        try {
            DailyTracker dt = new DailyTracker("12-12-12", 120, 2500);
            DailyTrackerRecord dtr = new DailyTrackerRecord();
            dtr.addDailyTracker(dt);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDailyTrackerRecord() {
        try {
            DailyTrackerRecord dtr = new DailyTrackerRecord();
            DailyTracker dt = new DailyTracker("12-12-12", 150, 2500);
            dtr.addDailyTracker(dt);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDailyTrackerRecord.json");
            writer.open();
            writer.write(dtr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDailyTrackerRecord.json");
            dtr = reader.read();
            List<DailyTracker> list = dtr.getRecord();
            checkDailyTrackerFields("12-12-12", 2500, 150, 0, 0, 0, 0, 0, list.get(0));
  
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @SuppressWarnings("methodlength")
    @Test
    void testWriterGeneralDailyTrackerRecord() {
        try {
            DailyTrackerRecord dtr = new DailyTrackerRecord();
            DailyTracker dt = new DailyTracker("12-12-12", 150, 2500);
            DailyTracker dt2 = new DailyTracker("12-1-12", 120, 2400);
            Food f1 = new Food("burger", new Calories(400), new Protein(20), new Carbohydrates(30), new Fat(10));
            Food f2 = new Food("salad", new Calories(250), new Protein(15), new Carbohydrates(25), new Fat(5));
            Food f3 = new Food("salad2", new Calories(250), new Protein(15), new Carbohydrates(25), new Fat(5));
            dt.addFood(f1);
            dt2.addFood(f2);
            dt2.addFood(f3);
            dt2.setCaloriesBurned(100);

            dtr.addDailyTracker(dt);
            dtr.addDailyTracker(dt2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDailyTrackerRecord.json");
            writer.open();
            writer.write(dtr);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralDailyTrackerRecord.json");
            dtr = reader.read();

            List<DailyTracker> list = dtr.getRecord();
            assertEquals(2, list.size());

            //testing dtr and dt, dt2 are correctly wrtten
            checkDailyTrackerFields("12-12-12", 2500, 150, 0, 400, 20, 30, 10, list.get(0));
            checkDailyTrackerFields("12-1-12", 2400, 120, 100, 500, 30, 50, 10, list.get(1));

            //testing f1,f2,f3 are correctly written
            checkFood("burger", 400, 20, 30, 10, list.get(0).getFoodRecord().get(0));
            checkFood("salad", 250,15,25,5, list.get(1).getFoodRecord().get(0));
            checkFood("salad2", 250,15,25,5, list.get(1).getFoodRecord().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}