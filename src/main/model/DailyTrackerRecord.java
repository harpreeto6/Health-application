package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/*
 * This class can store various instances of DailyTracker 
 * and act as a database from which user might be able to get information 
 * about previous days 
 */
public class DailyTrackerRecord implements Writable{

    private List<DailyTracker> trackerRecord;

    //EFFECT: constructs a new record, initialize the field trackerRecord
    public DailyTrackerRecord() {
        trackerRecord = new ArrayList<>();
    }

    //MODIFY: this
    //EFFECT: add a dailyTracker to trackerRecord if there doesn't exist a
    //        dailyTracker for the same date and return true, else return false
    public boolean addDailyTracker(DailyTracker dailyTracker) {
        String date = dailyTracker.getDate();
        boolean exist = false;
        for (DailyTracker tracker : trackerRecord) {
            if (tracker.getDate() == date) {
                exist = true;
            }
        }
        if (exist) {
            return false;
        } else {
            trackerRecord.add(dailyTracker);
            return true;
        }
    }

    //EFFECT: return trackerRecord field
    public List<DailyTracker> getRecord() {
        return trackerRecord;
    }

    @Override
    public JSONObject toJson() {
        // stub
        return null;
    }

    // EFFECTS: returns trackerRecord in this DailyTrackerRecord as a JSON array
    private JSONArray thingiesToJson() {
        //stub
        return null;
    }
}
