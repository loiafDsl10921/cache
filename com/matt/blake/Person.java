package com.matt.blake;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.LinkedHashMap;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public abstract class Person implements Cachable {
    @Expose(serialize = true, deserialize = true)
    protected String ssn;
    // protected Date birthdate;
    @Expose(serialize = true, deserialize = true)
    protected String birthday;
    // protected LocalDate birthday;
    @Expose(serialize = true, deserialize = true)
    protected String name;
    @Expose(serialize = true, deserialize = true)
    protected String sex;
    @Expose(serialize = true, deserialize = true)
    protected LinkedHashMap<Integer, String> cache;
    @Expose(serialize = true, deserialize = true)
    protected Gson gson;
    @Expose(serialize = true, deserialize = true)
    protected String jSonStr;

    // protected String inputValue;

    public Person() {
        this.name = "";
        this.sex = "";
        // inputValue = "";
        this.birthday = null;
        this.ssn = null;
        cache = new LinkedHashMap<Integer, String>(20, (float) .95, true);
        gson = new Gson();
        jSonStr = "";
    }

    public Person(String nameStr, String sexStr, String birthDate, String ssnStr) {
        this.name = nameStr;
        this.sex = sexStr;
        this.birthday = birthDate;
        this.ssn = ssnStr;
        cache = new LinkedHashMap<Integer, String>(20, (float) .95, true);
        gson = new Gson();
        jSonStr = "";
    }

    public void setCache(LinkedHashMap<Integer, String> cache) {
        this.cache = cache;
    }

    // public void addNumber(char c){
    // if (c.IsNumeric()){
    // inputValue
    // }

    // public Person(String text, String type, String number) {
    // name = text;
    // age = number;
    // sex = type;
    // }

    public Double getAge() {
        LocalDate today = LocalDate.now(); // Today's date
        System.out.println("Bday String " + this.birthday);
        String[] birthdayArgs = this.birthday.split("[/]", 0);
        return Double
                .parseDouble(
                        Period.between(
                                LocalDate.of(Integer.parseInt(birthdayArgs[2]),
                                        Month.of(Integer.parseInt(birthdayArgs[0])), Integer.parseInt(birthdayArgs[1])),
                                today).toString());
    }

    public void setBirthdate(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String text) {
        this.name = text;
    }

    public void setSSN(String ssn) {
        this.ssn = ssn;
    }

    public String getSSN() {
        return this.ssn;
    }

    public void setSex(String type) {
        this.sex = type;
    }

    public String getSex() {
        return this.sex;
    }

    public void setJson(String json) {
        this.jSonStr = json;
    }

    public String getJson() {
        return this.jSonStr;
    }

    @Override
    public void store() {
        // TODO Auto-generated method stub
        // gson.registerTypeAdapter()
        try {
            // this.jSonStr = gson.toJson((Person) obj); // serializes target to Json
            if (!this.jSonStr.isEmpty() & cache.get(this.jSonStr.hashCode()) == null) {
                cache.put(jSonStr.hashCode(), this.jSonStr);
                // System.out.println(cache.get(this.jSonStr.hashCode()) + "Added to the
                // cache.");
                System.out.println(this.name + " added to the cache.");
            } else {
                System.out.println(cache.get(this.jSonStr.hashCode()) + " - Already Exists in the Cache.");
            }

        } catch (Exception e) {
            System.out.println("Could not generate Json." + e.toString());
        }

    }

    @Override
    public String retrieve(Object obj) {
        // TODO Auto-generated method stub
        try {
            // return gson.toJson((Person) obj);
            return this.jSonStr;
        } catch (Exception e) {
            return "";
        }

    }

    public String toString() {
        return "Name: " + this.name + ", Age: " + this.getAge();
    }

}