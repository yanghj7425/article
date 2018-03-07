package com.yhj.chapter19;

public enum Shrubbery {
    GROUND("this is  ground"),
    UPPER(" this is upper"),
    LOWER("this is lower");

    private String description;


    private Shrubbery(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
