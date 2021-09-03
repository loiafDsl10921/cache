package com.matt.blake;

public class PersonFactory {

    private String personType;

    public PersonFactory() {
        this.personType = "";
    }

    public PersonFactory(String type) {
        this.personType = type;
    }

    public void setPersonType(String typeStr) {
        this.personType = typeStr;
    }

    public String getPersonType() {
        return this.personType;
    }

    public Person getPerson() {

        if (this.personType == null) {
            return null;
        }
        if (this.personType.equals("Athlete")) {
            return new Athlete();
        }
        if (this.personType.equals("Musician")) {
            return new Musician();
        }

        return null;

    }

}
