package persistence;

import org.json.JSONObject;

/*
 * represents an interface which make sure every class implementing this interface
 *  will have a method (toJson()) to convert its field and other details into json object which 
 * can be written into a json file
 */
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
