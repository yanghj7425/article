package com.yhj.chapter14;

import javafx.geometry.Pos;

public class Position {
    private String title;
    private Person person;

    public Position(String title, Person person) {
        this.title = title;
        this.person = person;
        if (person == null){  // 如果对象为空，则赋值一个空对象。
            this.person = Person.NULL;
        }
    }

    public Position() {
        this.person = Person.NULL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public static void main(String[] args) {
        Position position = new Position();
        if (position.getPerson() == Person.NULL){
            System.out.println(position.getPerson());
        }
    }
}
