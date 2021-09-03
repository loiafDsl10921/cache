package com.matt.blake;

import java.time.LocalDate;

import com.google.gson.annotations.Expose;

public class Athlete extends Person {
    @Expose(serialize = true, deserialize = true)
    private String sport;

    public Athlete() {
        super();
    }

    public Athlete(String text, String type, String birthdate, String sportName) {
        super(text, type, birthdate, sportName);
        sport = sportName;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String text) {
        sport = text;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Age: " + this.getAge() + ", Sport: " + this.getSport();
    }
}
