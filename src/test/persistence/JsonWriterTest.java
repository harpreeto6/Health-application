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
            List<DailyTracker> list = dtr.getRecord();
            assertTrue(list.get(0).getDate().equals("12-12-12"));
            assertEquals(150,list.get(0).getProteinGoal());
            assertEquals(2500, list.get(0).getCaloriesGoal());
            
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
            dtr.addDailyTracker(dt);
            dtr.addDailyTracker(dt2);

            dt2.setCaloriesBurned(100);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDailyTrackerRecord.json");
            writer.open();
            writer.write(dtr);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralDailyTrackerRecord.json");
            dtr = reader.read();

            List<DailyTracker> list = dtr.getRecord();

            assertEquals(2, list.size());

            //testing dtr and dt are correctly wrtten
            assertEquals(2500, list.get(0).getCaloriesGoal());
            assertEquals(150, list.get(0).getProteinGoal());
            assertEquals(400, list.get(0).getCaloriesConsumed());
            assertEquals(20, list.get(0).getProteinConsumed());
            assertEquals(30, list.get(0).getCarbohydratesConsumed());
            assertEquals(10, list.get(0).getFatConsumed());
            assertEquals(400, list.get(0).getFoodRecord().get(0).getCalories().getValue());
            assertEquals(20, list.get(0).getFoodRecord().get(0).getProtein().getValue());
            assertEquals(30, list.get(0).getFoodRecord().get(0).getCarbohydates().getValue());
            assertEquals(10, list.get(0).getFoodRecord().get(0).getFat().getValue());
            assertTrue(list.get(0).getFoodRecord().get(0).getName().equals("burger"));

            //testing dtr and dt2 are correctly written
            assertEquals(2400, list.get(1).getCaloriesGoal());
            assertEquals(120, list.get(1).getProteinGoal());
            assertEquals(500, list.get(1).getCaloriesConsumed());
            assertEquals(30, list.get(1).getProteinConsumed());
            assertEquals(50, list.get(1).getCarbohydratesConsumed());
            assertEquals(10, list.get(1).getFatConsumed());
            assertEquals(250, list.get(1).getFoodRecord().get(0).getCalories().getValue());
            assertEquals(15, list.get(1).getFoodRecord().get(0).getProtein().getValue());
            assertEquals(25, list.get(1).getFoodRecord().get(0).getCarbohydates().getValue());
            assertEquals(5, list.get(1).getFoodRecord().get(0).getFat().getValue());
            assertTrue(list.get(1).getFoodRecord().get(0).getName().equals("salad"));
            assertEquals(100, list.get(1).getCaloriesBurned());

            //testing f3 in dt2 is correctly written
            assertEquals(250, list.get(1).getFoodRecord().get(1).getCalories().getValue());
            assertEquals(15, list.get(1).getFoodRecord().get(1).getProtein().getValue());
            assertEquals(25, list.get(1).getFoodRecord().get(1).getCarbohydates().getValue());
            assertEquals(5, list.get(1).getFoodRecord().get(1).getFat().getValue());
            assertTrue(list.get(1).getFoodRecord().get(1).getName().equals("salad2"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}