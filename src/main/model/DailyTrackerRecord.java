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
public class DailyTrackerRecord implements Writable {

    private List<DailyTracker> trackerRecord;

    //EFFECT: constructs a new record, initialize the field trackerRecord
    public DailyTrackerRecord() {
        trackerRecord = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New DailyTrackerRecorder Constructed "));
    }

    //MODIFY: this
    //EFFECT: 1) add a dailyTracker to trackerRecord 
    //        2) if there doesn't exist a dailyTracker for the same date return true
    //        3) if there already exists a dailyTracker for same date remove that and return false
    public boolean addDailyTracker(DailyTracker dailyTracker) {
        String date = dailyTracker.getDate();
        boolean exist = false;
        for (DailyTracker tracker : trackerRecord) {
            if (tracker.getDate() == date) {
                exist = true;
            }
        }      
        if (exist) {
            trackerRecord.remove(dailyTracker);
            trackerRecord.add(dailyTracker);
            EventLog.getInstance().logEvent(new Event("DailyTracker for " 
                                                    + dailyTracker.getDate() + " saved inside DailyTrackerRecord "));
            return false;
        } else {
            trackerRecord.add(dailyTracker);
            EventLog.getInstance().logEvent(new Event("DailyTracker for " 
                                                    + dailyTracker.getDate() + " saved inside DailyTrackerRecord "));
            return true;
        }
    }

    //REQUIRE: date should be in correct format DD-MM-YY where D,M,Y are int
    //EFFECT: return -1 if trackerRecord doesn't contain dailyTracker with current date or else
    //        return index of DailyTracker in trackerRecord
    public int contains(String date) {

        EventLog.getInstance().logEvent(new Event("Record for " 
                                                    + date + " date searched inside DailyTrackerRecordClass"));

        boolean exists = false;
        int index = -1;
        for (DailyTracker dt : trackerRecord) {
            index++;
            if (dt.getDate().equals(date)) {
                exists = true;
                break;
            }
        }
        if (exists == false) {
            return -1;
        } else {
            return index;
        }
    }

    //EFFECT: return trackerRecord field
    public List<DailyTracker> getRecord() {
        return trackerRecord;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("trackerRecord", trackerRecordToJson());
        return json;
    }

    // EFFECTS: returns trackerRecord in this DailyTrackerRecord as a JSON array
    private JSONArray trackerRecordToJson() {

        JSONArray jsonArray = new JSONArray();
        for (DailyTracker f : trackerRecord) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}
