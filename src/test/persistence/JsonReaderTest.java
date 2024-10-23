package persistence;

import model.DailyTracker;
import model.DailyTrackerRecord;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            DailyTrackerRecord dtr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDailyTrackerRecord() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDailyTrackerRecord.json");
        try {
            DailyTrackerRecord dtr = reader.read();
            assertEquals(0, dtr.getRecord().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @SuppressWarnings("methodlength")
    @Test
    void testReaderGeneralDailyTrackerRecord() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDailyRecordTracker.json");
        try {
            DailyTrackerRecord dtr = reader.read();
            List<DailyTracker> list = dtr.getRecord();
            assertEquals(2, list.size());
            assertTrue(dtr.getRecord().get(0).getFoodRecord().get(0).getName().equals("Burg"));
            assertEquals(2500, list.get(0).getCaloriesGoal());
            assertEquals(150, list.get(0).getProteinGoal());
            assertEquals(20, list.get(0).getFoodRecord().get(0).getProtein().getValue());
            assertEquals(30, list.get(0).getFoodRecord().get(0).getCarbohydates().getValue());
            assertEquals(10, list.get(0).getFoodRecord().get(0).getFat().getValue());
            assertEquals(0, list.get(0).getCaloriesBurned());
            assertTrue(list.get(0).getDate().equals("12-12-12"));

            assertEquals(2400, list.get(1).getCaloriesGoal());
            assertEquals(120, list.get(1).getProteinGoal());
            assertEquals(200, list.get(1).getFoodRecord().get(0).getCalories().getValue());
            assertEquals(10, list.get(1).getFoodRecord().get(0).getProtein().getValue());
            assertEquals(10, list.get(1).getFoodRecord().get(0).getCarbohydates().getValue());
            assertEquals(0, list.get(1).getFoodRecord().get(0).getFat().getValue());
            assertEquals(100, list.get(1).getCaloriesBurned());
            assertTrue(list.get(1).getDate().equals("13-12-22"));
            assertTrue(list.get(1).getFoodRecord().get(0).getName().equals("salad"));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    
}