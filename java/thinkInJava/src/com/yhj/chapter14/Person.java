package com.yhj.chapter14;

public class Person {
    public final String first;
    public final String last;
    public final String address;

    public static final Person NULL = new NullPerson();

    public Person(String first, String last, String address) {
        this.first = first;
        this.last = last;
        this.address = address;
    }
    public static class NullPerson extends Person implements Null{
        private NullPerson(){
            super("Nonw","None","None");
        }

        @Override
        public String toString() {
                return "NullPerson";
        }
    }

}
