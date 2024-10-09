package model;

import java.util.ArrayList;
import java.util.List;

/*
 * This class can store various instances of DailyTracker 
 * and act as a database from which user might be able to get information 
 * about previous days 
 */
public class DailyTrackerRecord {

    private List<DailyTracker> dailytrackerRecord;

    //EFFECT: constructs a new record, initialize the field trackerRecord
    public DailyTrackerRecord() {
        dailytrackerRecord = new ArrayList<>();
    }

    //MODIFY: this
    //EFFECT: add a dailyTracker to trackerRecord if there doesn't exist a
    //        dailyTracker for the same date and return true, else return false
    public boolean addDailyTracker(DailyTracker dailyTracker) {
        String date = dailyTracker.getDate();
        boolean exist = false;
        for (DailyTracker tracker : dailytrackerRecord) {
            if (tracker.getDate() == date) {
                exist = true;
            }
        }
        if (exist) {
            return false;
        } else {
            dailytrackerRecord.add(dailyTracker);
            return true;
        }
    }

    //EFFECT: return trackerRecord field
    public List<DailyTracker> getRecord() {
        return dailytrackerRecord;
    }
}
