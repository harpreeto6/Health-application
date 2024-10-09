package model;

import java.util.ArrayList;
import java.util.List;

public class DailyTrackerRecord {

    private List<DailyTracker> DailytrackerRecord;

    //MODIFY: this
    //EFFECT: constructs a new record, initialize the field trackerRecord
    public DailyTrackerRecord() {
        DailytrackerRecord = new ArrayList<>();
    }

    //MODIFY: this
    //EFFECT: add a dailyTracker to trackerRecord if there already doesn't 
    //exist a tracker for the same date in trackRecord
    public boolean addDailyTracker(DailyTracker dailyTracker) {
        String date = dailyTracker.getDate();
        boolean exist = false;
        for (DailyTracker tracker : DailytrackerRecord) {
            if (tracker.getDate() == date) {
                exist = true;
            }
        }
        if(exist) {
            return false;
        } else {
            DailytrackerRecord.add(dailyTracker);
            return true;
        }
    }

    //EFFECT: return trackerRecord field
    public List<DailyTracker> getDailytrackerRecord() {
        return DailytrackerRecord;
    }
}
