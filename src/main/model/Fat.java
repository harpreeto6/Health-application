package model;

import org.json.JSONObject;

import persistence.Writable;

/**
 * Class representing the fat (Macro-nutrient) present inside food
 * **/
public class Fat extends MacroNutrient implements Writable {

    //EFFECT: Constructs a Fat class by setting value (super field) field as given value
    public Fat(double value) {
        super(value);
        //this.value = value;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        return json;
    }


}
