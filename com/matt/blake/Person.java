package com.matt.blake;

public class Person {

    private String age;
    private String name;
    private String sex;

    public Person() {
    }

    public Person(String text, String type, String number) {
        name = text;
        age = number;
        sex = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String number) {
        age = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String text) {
        name = text;
    }

    public void setSex(String type) {
        sex = type;
    }

    public String getSex() {
        return sex;
    }

}