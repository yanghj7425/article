package com.yhj.chapter14.ClassVarable;

public class Face {
    private int i=8;
    public static int j=9;

    {
        System.out.println("initilize code block " + j + "\t" +i);

    }

    static {
        System.out.println("static code block " + j );
        j = 1;
    }
    @Override
    public String toString() {
        return "Face{" +
                "i=" + i + "\t" + "j=" + j +
                '}';
    }


}
