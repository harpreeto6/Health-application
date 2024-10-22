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
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
            Food f1 = new Food("burger", new Calories(400), new Protein(20), new Carbohydrates(30), new Fat(10));
            dt.addFood(f1);
            dtr.addDailyTracker(dt);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDailyTrackerRecord.json");
            writer.open();
            writer.write(dtr);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralDailyTrackerRecord.json");
            dtr = reader.read();
            assertEquals(400, dtr.getRecord().get(0).getCaloriesConsumed());
            assertEquals(20, dtr.getRecord().get(0).getProteinConsumed());
            assertEquals(30, dtr.getRecord().get(0).getCarbohydratesConsumed());
            assertEquals(10, dtr.getRecord().get(0).getFatConsumed());
            assertEquals(400, dtr.getRecord().get(0).getFoodRecord().get(0).getCalories().getValue());
            assertEquals(20, dtr.getRecord().get(0).getFoodRecord().get(0).getProtein().getValue());
            assertEquals(30, dtr.getRecord().get(0).getFoodRecord().get(0).getCarbohydates().getValue());
            assertEquals(10, dtr.getRecord().get(0).getFoodRecord().get(0).getFat().getValue());
            assertTrue(dtr.getRecord().get(0).getFoodRecord().get(0).getName().equals("burger"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}