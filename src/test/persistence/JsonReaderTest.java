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

    @Test
    void testReaderGeneralDailyTrackerRecord() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDailyRecordTracker.json");
        try {
            DailyTrackerRecord dtr = reader.read();
            List<DailyTracker> list = dtr.getRecord();
            assertEquals(2, list.size());

            

            checkDailyTrackerFields("12-12-12", 2500, 150, 0, 400, 20, 30, 10, list.get(0));
            checkDailyTrackerFields("13-12-22", 2400, 120, 100, 200, 10, 10, 0, list.get(1));

            checkFood("Burg", 400, 20, 30, 10, list.get(0).getFoodRecord().get(0));
            checkFood("salad", 200, 10, 10, 0, list.get(1).getFoodRecord().get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    
}