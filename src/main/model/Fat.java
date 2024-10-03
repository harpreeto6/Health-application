package model;

/**
 * Class representing the Fat attribute of food
 * **/
public class Fat {

    private int value;

    //EFFECT: Construct a Fat attribute without setting any field
    public Fat() {
        //stub
    }

    //EFFECT: Constructs a Fat attribute by setting it's field as given value
    public Fat(int value) {
        this.value = value;
    }

    //REQUIRES: value >= 0
    //MODIFIES: This
    //EFFECT: set value field to given value 
    public void setValue(int value) {
        this.value = value;
    }

    //EFFECT: return value field 
    public int getValue() {
        return value;
    }


}
