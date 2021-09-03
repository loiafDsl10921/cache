package com.matt.blake;

import com.google.gson.annotations.Expose;

public class Musician extends Person {
    @Expose(serialize = true, deserialize = true)
    private String instrument;

    public Musician() {
        super();
    }

    public Musician(String text, String type, String birthdate, String instrumentName) {
        super(text, type, birthdate, instrumentName);
        instrument = instrumentName;
    }

    public void setInstrument(String str) {
        instrument = str;
    }

    public String getInstrument() {
        return instrument;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Age: " + this.getAge() + ", Instrument: " + this.getInstrument();
    }
}
