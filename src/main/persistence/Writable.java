package persistence;

import org.json.JSONObject;

/*
 * represents an interface to make sure it's obects can be converted into JSONObjects
 */
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
