package persistence;

import model.Food;
import model.DailyTracker;
import model.DailyTrackerRecord;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;

import model.Protein;
import model.Carbohydrates;
import model.Fat;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            DailyTrackerRecord dtr = reader.read();
            assertEquals(0, dtr.getRecord().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDailyTrackerRecord() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            DailyTrackerRecord dtr = reader.read();
            assertEquals(1, dtr.getRecord().size());
            List<DailyTracker> list = dtr.getRecord();
            assertEquals(1, list.size());
            assertTrue(dtr.getRecord().get(0).getFoodRecord().get(0).equals("burg"));
            assertEquals(2500, dtr.getRecord().get(0).getFoodRecord().get(0).getCalories().getValue(), 
                            list.get(0).getFoodRecord().get(0).getCalories().getValue());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    
}