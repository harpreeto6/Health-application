package model;

import org.json.JSONObject;

import persistence.Writable;

/**
 * Class representing the fat macro-nutrient present inside food
 * **/
public class Fat implements Writable {

    private double value;

    //EFFECT: Constructs a Fat attribute by setting it's field as given value
    public Fat(double value) {
        this.value = value;
    }

    //REQUIRES: value >= 0
    //MODIFIES: This
    //EFFECT: set value field to given value 
    public void setValue(double value) {
        this.value = value;
    }

    //EFFECT: return value field 
    public double getValue() {
        return this.value;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        return json;
    }


}
